package com.volunteer.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.volunteer.VolunteerManagementApplication;
import com.volunteer.component.RedisOperator;
import com.volunteer.entity.UmbrellaHistoryBorrow;
import com.volunteer.entity.UmbrellaOrder;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.common.DataFormats;
import com.volunteer.entity.vo.UmbrellaOrderListVo;
import com.volunteer.entity.vo.UmbrellaOrderVo;
import com.volunteer.mapper.UmbrellaMapper;
import com.volunteer.service.UmbrellaBorrowService;
import com.volunteer.service.VolunteerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: 梁峰源
 * @date: 2022/1/31 23:40
 * Description: UmbrellaController测试类
 */
@SpringBootTest(classes = VolunteerManagementApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class TestUmbrellaController {

    @Autowired
    private UmbrellaBorrowService umbrellaBorrowService;
    @Resource
    private UmbrellaMapper umbrellaMapper;
    @Autowired
    private VolunteerService volunteerService;
    @Resource
    private RedisOperator redisOperator;

    /**
     * 指定页码查询所有借取记录
     */
    @Test
    public void test01() {

    }

    /**
     * 指定用户分页查询历史借阅雨伞记录
     */
    @Test
    public void test02() {
    }

    /**
     *
     */
    @Test
    public void test03() {
        QueryWrapper<UmbrellaHistoryBorrow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openID", "+7BfWy6IijmcZFO4Ac8fjmAvS8=");
        Page<UmbrellaHistoryBorrow> page = new Page<>();
        page.setCurrent(1).setSize(20);
        IPage<UmbrellaHistoryBorrow> umbrellaBorrowIPage = umbrellaMapper.selectPage(page, queryWrapper);
        umbrellaBorrowIPage.getRecords().forEach(System.out::println);
    }

    @Test
    public void TestBorrowByVolunteer() throws InvocationTargetException, IllegalAccessException {
        Volunteer volunteer = volunteerService.getByOpenId("oR83j4kkq2CyvVmuxl6znKbrWi2A");
        Integer result = umbrellaBorrowService.borrowByVolunteer(volunteer);
        log.info("更新完的结果{}", result);
    }

    @Test
    public void TestReturnByVolunteer() {
        Volunteer volunteer = volunteerService.getByOpenId("oR83j4kkq2CyvVmuxl6znKbrWi2A");
        Integer result = umbrellaBorrowService.returnByVolunteer(volunteer);
        log.info("更新完的结果{}", result);
    }

    /**
     * 测试爱心雨伞在库记录用redis存储
     */
    @Test
    public void TestRedis() throws InstantiationException, IllegalAccessException {
//        Volunteer volunteer = volunteerService.getByOpenId("oR83j4kkq2CyvVmuxl6znKbrWi2A");
        UmbrellaHistoryBorrow umbrellaHistoryBorrow = umbrellaBorrowService.getById(5);
//        Map<String,UmbrellaBorrow> map = new HashMap<>();
//        map.put(volunteer.getOpenid(),umbrellaBorrow);
        String key = "umbrella:" + "钱七oR83j4kkq2CyvVmuxl6znKbrWi2A";
        try {
            parseMap(key, umbrellaHistoryBorrow);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        Map<Object, Object> entries = redisHash.entries("111");
//        Object borrowDate = entries.get("BorrowDate");
        Map<Object, Object> map = redisOperator.hgetall(key);
        log.info("收到的信息{}", map);

//        ValueOperations<String, Object> redisValue = redisTemplate.opsForValue();
//        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
//        UmbrellaBorrow o = (UmbrellaBorrow)entries.get(volunteer.getOpenid());
//        redisValue.set(key,umbrellaBorrow);
//        UmbrellaBorrow o = (UmbrellaBorrow)redisValue.get(key);
//        log.info("输出消息{}",o);
    }

    @Test
    public void TestUpdateBorrowDurationJob() {
        umbrellaBorrowService.updateBorrowDurationJob();
    }

    /**
     * 将bean转成map
     */
    private void parseMap(String key, Object bean) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        //2. 获得所有的get方法
        List<Method> allGetMethod = getAllGetMethod(bean);
        //3. 遍历存入值
        for (Method m : allGetMethod) {
            //截取属性名
            String field = m.getName().substring(3);
            //激活方法得到值
            Object value = m.invoke(bean) + "";//将LocalDataTime转换成字符串
            log.info("激活方法得到值:{}", value);
            //往redis里存这些字段
            redisOperator.hset(key, field, value);
        }

    }

    /**
     * 取出所有的get方法
     *
     * @param bean 指定实例
     * @return 返回set方法的集合
     */
    private List<Method> getAllGetMethod(Object bean) {
        List<Method> getMethods = new ArrayList<>();
        Method[] methods = bean.getClass().getMethods();
        for (Method m : methods) {
            if (m.getName().startsWith("get")) {
                getMethods.add(m);
            }
        }
        return getMethods;
    }

    @Test
    public void TestSelectBorrow() throws InvocationTargetException, IllegalAccessException {
        UmbrellaOrderListVo listVo = umbrellaBorrowService.selectBorrow(1, 20);
        listVo.getRecords().forEach(System.out::println);
    }
}

