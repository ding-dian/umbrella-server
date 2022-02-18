package com.volunteer.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.component.RedisOperator;
import com.volunteer.entity.Umbrella;
import com.volunteer.entity.UmbrellaHistoryBorrow;
import com.volunteer.entity.UmbrellaOrder;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.common.DataFormats;
import com.volunteer.entity.vo.UmbrellaHistoryVo;
import com.volunteer.entity.vo.UmbrellaOrderVo;
import com.volunteer.mapper.UmbrellaMapper;
import com.volunteer.service.MailService;
import com.volunteer.service.UmbrellaBorrowService;
import com.volunteer.service.VolunteerService;
import com.volunteer.util.AES;
import com.volunteer.util.BeanMapUtil;
import com.volunteer.util.SendMailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: 梁峰源
 * @date: 2022/1/26 21:05
 * Description: 爱心雨伞数据与数据库交互实现类，历史数据存入数据库，爱心雨伞的借取信息存入redis中
 */
@Service
@Slf4j
public class UmbrellaBorrowServiceImpl extends ServiceImpl<UmbrellaMapper, UmbrellaHistoryBorrow> implements UmbrellaBorrowService {


    @Value("${umbrella.umbrellaBorrowRecordPageSize}")
    private Integer defaultPageSize;
    @Value("${umbrella.defaultBorrowDuration}")
    private Integer defaultBorrowDuration;
    @Value("${AES-secret}")
    private String AESSecret;//手机号解密密钥
    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private SendMailUtil sendMailUtil;


    @Override
    public List<UmbrellaHistoryVo> selectHistoryById(Volunteer volunteer) {
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
        QueryWrapper<UmbrellaHistoryBorrow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openID", volunteer.getOpenid());
        log.info("pageNo:【{}】，pageSize:【{}】", volunteer.getPageNo(), volunteer.getPageSize());
        Page<UmbrellaHistoryBorrow> page = new Page<>();
        page.setCurrent(volunteer.getPageNo()).setSize(volunteer.getPageSize());
        return baseMapper.selectPage(page, queryWrapper)
                .getRecords()
                .stream()
                .map(UmbrellaHistoryVo::new)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String,Object> selectHistoryAll(Integer pageNo, Integer pageSize) {
        if (ObjectUtil.isNull(pageNo)){
            pageNo = 1;//没有输入页号默认返回第一页
        }
        if (ObjectUtil.isNull(pageSize)){
            pageSize = defaultPageSize;
        }
        IPage<UmbrellaHistoryBorrow> page = new Page<>(pageNo,pageSize);
        log.info("pageNo:【{}】，pageSize:【{}】", pageNo, pageSize);
        //数据库limit分页从第0条数据开始
        int skip = (pageNo - 1) * pageSize;
        List<UmbrellaHistoryBorrow> umbrellaHistoryBorrows = baseMapper.selectHistoryAll(skip,pageSize);
        List<UmbrellaHistoryVo> collect = umbrellaHistoryBorrows.stream()
                .map(UmbrellaHistoryVo::new)
                .collect(Collectors.toList());
        //数据库中数据总数
        int total = baseMapper.selectCount();
        //将数据包装一下
        Map<String,Object> map = new HashMap<>();
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("records",collect);
        map.put("Total",total);
        return map;
    }

    @Override
    public Map<String,Object> selectBorrow(Integer pageNo, Integer pageSize) {
        return getBeanForRedis("umbrellaBorrow*",pageNo,pageSize);
    }

    @Override
    public Map<String,Object> selectOvertime(Integer pageNo, Integer pageSize) {
        return getBeanForRedis("umbrellaOvertime*",pageNo,pageSize);
    }

    @Override
    public void deleteOvertime(String key) {
        redisOperator.del(key);
    }

    private Map<String,Object> getBeanForRedis(String key,Integer pageNo, Integer pageSize){
        //封装一个Map用来回传数据
        Map<String,Object> data = new HashMap<>();
        //从redis里拿到所有的keys
        Set<String> keys = redisOperator.keys(key);
        //存储一个redis中用户的总数量
        data.put("Total",  keys.size());
        //拿到所有map集合，一个map对应一个用户的信息
        List<Map<Object, Object>> collect = keys.stream()
                .map(redisOperator::hgetall)
                .collect(Collectors.toList());
        //将map转换为Bean对象
        List<UmbrellaOrder> list = new ArrayList<>();
        for (Map<Object, Object> map : collect) {
            UmbrellaOrder umbrellaOrder = new UmbrellaOrder();
            umbrellaOrder.setOpenID((String) map.get("openID"))
                    .setUserName((String) map.get("userName"))
                    .setPhoneNumber((String) map.get("phoneNumber"))
                    .setQqNumber((String) map.get("qqNumber"))
                    .setEmailAddress((String) map.get("emailAddress"))
                    .setBorrowDate(LocalDateTime.parse((String)map.get("borrowDate"), DataFormats.dateTimeFormatter))
                    .setBorrowDurations(Double.parseDouble((String)map.get("borrowDurations")))
                    .setStudentId(Long.parseLong((String) map.get("studentId")));
            //收集到一个对象后收集起来
            list.add(umbrellaOrder);
        }
        //检查pageNo不能为空且不能小于1，否者默认返回第一页
        pageNo = ObjectUtil.isNull(pageNo) || pageNo < 1 ? 1 : pageNo;
        //检查pageSize不能为空且不能小于1，默认一页返回20条数据
        pageSize = ObjectUtil.isNull(pageSize) || pageSize < 1 ? 20 : pageSize;
        //这里需要分页
        List<UmbrellaOrderVo> records = list.stream()
                //先排序，按照借伞时间倒序返回
                .sorted(Comparator.comparing(UmbrellaOrder::getBorrowDurations).reversed())
                .skip((pageNo - 1) * pageSize)//分页
                .limit(pageSize)
                .map(UmbrellaOrderVo::new)//转换为vo
                .collect(Collectors.toList());
        //存放返回的数据
        data.put("pageNo",pageNo);
        data.put("pageSize",pageSize);
        data.put("records",records);
        return data;
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
                .setBorrowDate(LocalDateTime.now())
                .setBorrowDurations(0.0)
                .setEmailAddress(volunteer.getQqNumber()+"@qq.com");
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
        Double durations = getDuration(map.get("borrowDate"));
        //将缓存里的数据删除
        redisOperator.del(key);
        //往umbrella_borrow表添加一条历史数据
        UmbrellaHistoryBorrow umbrellaHistoryBorrow = new UmbrellaHistoryBorrow();
        umbrellaHistoryBorrow.setOpenID((String) map.get("openID"))
                .setBorrowDate(LocalDateTime.parse((String)map.get("borrowDate"),DataFormats.dateTimeFormatter))
                .setBorrowDurations(durations)
                .setReturnDate(LocalDateTime.now())
                .setBorrowStatus(1);//1表示已归还
        return baseMapper.insert(umbrellaHistoryBorrow);
    }


    @Override
    public Integer deleteOneRecordByVolunteer(UmbrellaHistoryBorrow umbrellaHistoryBorrow) {
        return null;
    }

    @Override
    public Integer updateUmbrellaBorrowRecordByID(UmbrellaHistoryBorrow umbrellaHistoryBorrow) {
        return null;
    }

    @Override
    public Integer updateUmbrellaStatus(Umbrella umbrella) {
        return null;
    }

    @Override
    public void updateBorrowDurationJob() {
        //拿到所有的以umbrellaBorrow开头的key
        Set<String> keys = redisOperator.keys("umbrellaBorrow*");
        if(keys.isEmpty()) return;
        //保存逾期用户信息
        Map<String,Double> overtimeUser = new HashMap<>();
        //遍历每一个用户，更新他们借取的时间
        for(String key : keys){
            String borrowDate = redisOperator.hget(key, "borrowDate");
            //获得时间间隔
            Double duration = getDuration(borrowDate);
            //判断用户是否超时
            if(duration > defaultBorrowDuration){
                //将超时用户信息记录
                overtimeUser.put(key,duration);
            }else {
                //用户在和合法时间内更新借取时间
                redisOperator.hset(key,"borrowDurations",Double.toString(duration));
            }
        }
        //执行超时策略
        borrowOvertimeHandle(overtimeUser);
    }

    @Override
    public void borrowOvertimeHandle(Map<String,Double> overTimeUser) {
        //将超时用户的姓名和电话qq号拼接在一起，统一发给管理员
        StringBuilder sb = new StringBuilder();
        overTimeUser.forEach((key, duration) -> {
            //更新借取时间
            redisOperator.hset(key,"borrowDurations", Double.toString(duration));
            //拿到预期用户的邮箱
            String emailAddress = redisOperator.hget(key, "emailAddress");
            //发送警告邮件给用户
            sendMailUtil.sendOverTimeAlarm(new String[]{emailAddress});
            //将用户信息保存一个，保存姓名，电话，qq，借伞时间
            String userName = redisOperator.hget(key, "userName");
            String phoneNumber = redisOperator.hget(key, "phoneNumber");
            String qqNumber = redisOperator.hget(key, "qqNumber");
            String opID = redisOperator.hget(key, "openID");
            sb.append("用户名：").append(userName)
                    .append("，电话:").append(phoneNumber)
                    .append("，qq：").append(qqNumber)
                    .append("，借取时间：").append(duration).append("\n");
            //修改redis中用户的key，将其保存到超时key集合中
            redisOperator.rename(key,"umbrellaOvertime:"+userName+opID);
        });

        //将所有逾期用户信息发送给管理员
        sendMailUtil.send2Admin(sb.toString());
    }



    /**
     * 将bean存入Redis中
     */
    private void parseRedisMap(String key, Object bean) {
        Map<String, Object> map = BeanMapUtil.beanToMap(bean);
        map.forEach((field,value)->redisOperator.hset(key,field, String.valueOf(value)));


        //        // 获得所有的get方法
//        List<Method> allGetMethod = getAllGetMethod(bean);
//        // 遍历存入值
//        for(Method m : allGetMethod){
//            //截取属性名
//            String field = m.getName().substring(3);
//            //激活方法得到值
//            String value = m.invoke(bean)+"";//将LocalDataTime转换成字符串
//            log.info("激活了方法："+value);
//            //往redis里存这些字段
//            redisOperator.hset(key,field,value);
//        }

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
     * @param borrowDate 借取雨伞时候的时间，格式为yyyy-MM-dd'T'HH:mm:ss.SSS
     * @return 借伞时长，不足30分钟按30分钟算
     */
    private Double getDuration(Object borrowDate){
        //没有取到借伞时间
        if(ObjectUtil.isNull(borrowDate)){
            log.error("redis服务异常，未取到缓存数据");
            throw new RuntimeException("服务器读取缓存异常，读取不到用户的BorrowDate数据");
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
