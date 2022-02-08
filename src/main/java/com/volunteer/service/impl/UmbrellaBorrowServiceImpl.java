package com.volunteer.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.component.RedisOperator;
import com.volunteer.controller.UmbrellaController;
import com.volunteer.entity.Umbrella;
import com.volunteer.entity.UmbrellaBorrow;
import com.volunteer.entity.UmbrellaOrder;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.common.DataFormats;
import com.volunteer.entity.common.Result;
import com.volunteer.mapper.UmbrellaMapper;
import com.volunteer.service.UmbrellaBorrowService;
import com.volunteer.service.VolunteerService;
import com.volunteer.util.AES;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: 梁峰源
 * @date: 2022/1/26 21:05
 * Description: 爱心雨伞数据与数据库交互实现类，历史数据存入数据库，爱心雨伞的借取和归还存入redis中
 */
@Service
@Slf4j
public class UmbrellaBorrowServiceImpl extends ServiceImpl<UmbrellaMapper, UmbrellaBorrow> implements UmbrellaBorrowService {


    @Value("${umbrella.umbrellaBorrowRecordPageSize}")
    private Integer defaultPageSize;
    @Value("${AES-secret}")
    private String AESSecret;//手机号解密密钥
    @Autowired
    private RedisOperator redisOperator;
    @Resource
    private VolunteerService volunteerService;

    @Override
    public IPage<UmbrellaBorrow> selectList(Volunteer volunteer) {
        if (ObjectUtil.isNull(volunteer) || ObjectUtil.isNull(volunteer.getOpenid())) {
            //没有传入查询人或者查询人没有openID，直接返回
            log.error("未传入用户信息或用户信息不正确");
            return null;
        }
        if (ObjectUtil.isNull(volunteer.getPageNo()) || volunteer.getPageNo() == 0) {
            //没有设置请求的页号默认返回第一页
            volunteer.setPageNo(1);
        }
        if (ObjectUtil.isNull(volunteer.getPageSize()) || volunteer.getPageSize() == 0) {
            //没有设置每页请求的数据，默认返回二十条数据
            volunteer.setPageSize(defaultPageSize);
        }
        QueryWrapper<UmbrellaBorrow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openID", volunteer.getOpenid());
        log.info("pageNo:【{}】，pageSize:【{}】", volunteer.getPageNo(), volunteer.getPageSize());
        Page<UmbrellaBorrow> page = new Page<>();
        page.setCurrent(volunteer.getPageNo()).setSize(volunteer.getPageSize());
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public IPage<UmbrellaBorrow> selectAll(Integer pageNo, Integer pageSize) {
        if (ObjectUtil.isNull(pageNo)){
            pageNo = 1;//没有输入页号默认返回第一页
        }
        if (ObjectUtil.isNull(pageSize)){
            pageSize = defaultPageSize;
        }
        IPage<UmbrellaBorrow> page = new Page<>(pageNo,pageSize);
        log.info("pageNo:【{}】，pageSize:【{}】", pageNo, pageSize);
        return baseMapper.selectPage(page,null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer borrowByVolunteer(Volunteer volunteer) throws InvocationTargetException, IllegalAccessException {
        //拼接openID为了防止姓名重复
        String key = "umbrellaBorrow:"+volunteer.getName()+volunteer.getOpenid();
        if(ObjectUtil.isNull(volunteer) || redisOperator.exists(key)){
            return -1;//返回-1表示未传入用户或该用户已经借取过雨伞了，不能借取
        }
        String phoneNumber = null;
        try {
            //将手机号解密
            phoneNumber = AES.decrypt(AES.base64Decode(AES.aesDecrypt(volunteer.getPhoneNumber())),AESSecret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UmbrellaOrder umbrellaOrder = new UmbrellaOrder();
        umbrellaOrder.setOpenID(volunteer.getOpenid())
                .setUserName(volunteer.getName())
                .setStudentId(volunteer.getStudentId())
                .setQqNumber(volunteer.getQqNumber())
                .setPhoneNumber(phoneNumber)
                .setBorrowDate(LocalDateTime.now());
        //将bean存入redis中
        parseRedisMap(key,umbrellaOrder);
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer returnByVolunteer(Volunteer volunteer) {
        //拼接openID为了防止姓名重复
        String key = "umbrellaBorrow:"+volunteer.getName()+volunteer.getOpenid();
        if(ObjectUtil.isNull(volunteer) || ! redisOperator.exists(key)){
            return -1;//返回-1表示未传入用户或该用户并没有借伞记录，不能还伞
        }
        Map<Object, Object> map = redisOperator.hgetall(key);
        //获得借伞时间时间段
        Double durations = getDuration(map.get("BorrowDate"));
        //将缓存里的数据删除
        redisOperator.del(key);
        //往umbrella_borrow表添加一条历史数据
        UmbrellaBorrow umbrellaBorrow = new UmbrellaBorrow();
        umbrellaBorrow.setOpenID((String) map.get("OpenID"))
                .setBorrowDate(LocalDateTime.parse((String)map.get("BorrowDate"),DataFormats.dateTimeFormatter))
                .setBorrowDurations(durations)
                .setReturnDate(LocalDateTime.now())
                .setBorrowStatus(1);//1表示已归还
        return baseMapper.insert(umbrellaBorrow);
    }


    @Override
    public Integer deleteOneRecordByVolunteer(UmbrellaBorrow umbrellaBorrow) {
        return null;
    }

    @Override
    public Integer updateUmbrellaBorrowRecordByID(UmbrellaBorrow umbrellaBorrow) {
        return null;
    }

    @Override
    public Integer updateUmbrellaStatus(Umbrella umbrella) {
        return null;
    }

    /**
     * 将bean存入Redis中
     */
    private void parseRedisMap(String key, Object bean) throws IllegalAccessException, InvocationTargetException {
        // 获得所有的get方法
        List<Method> allGetMethod = getAllGetMethod(bean);
        // 遍历存入值
        for(Method m : allGetMethod){
            //截取属性名
            String field = m.getName().substring(3);
            //激活方法得到值
            String value = m.invoke(bean)+"";//将LocalDataTime转换成字符串
            log.info("激活了方法："+value);
            //往redis里存这些字段
            redisOperator.hset(key,field,value);
        }

    }

    /**
     * 取出所有的get方法
     *
     * @param bean 指定实例
     * @return 返回get方法的集合
     */
    private List<Method> getAllGetMethod(Object bean) {
        List<Method> getMethods = new ArrayList<>();
        Method[] methods = bean.getClass().getMethods();
        for (Method m : methods) {
            if (m.getName().startsWith("get") && !m.getName().equalsIgnoreCase("getClass")) {
                getMethods.add(m);
            }
        }
        return getMethods;
    }

    /**
     * 获得借取雨伞的时间间隔
     * @param borrowDate 借取雨伞时候的时间，格式为yyyy-MM-ddTHH:mm:ss
     * @return 借伞时长，不足30分钟按30分钟算
     */
    private Double getDuration(Object borrowDate){
        //没有取到借伞时间
        if(ObjectUtil.isNull(borrowDate)){
            log.error("redis服务异常，未取到缓存数据");
            return 0.0;
        }
        LocalDateTime borrowTime = LocalDateTime.parse((String)borrowDate, DataFormats.dateTimeFormatter);
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(borrowTime,now);
        //借伞分钟
        long millis = duration.toMinutes();
        //不足30分钟按30分钟算
        return millis / 60 + (millis % 60 > 30 ? 1 : 0.5);
    }
}
