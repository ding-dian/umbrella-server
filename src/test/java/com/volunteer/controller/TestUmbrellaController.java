package com.volunteer.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.volunteer.VolunteerManagementApplication;
import com.volunteer.entity.UmbrellaBorrow;
import com.volunteer.entity.Volunteer;
import com.volunteer.mapper.UmbrellaMapper;
import com.volunteer.service.UmbrellaBorrowService;
import com.volunteer.service.VolunteerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    @Autowired
    private UmbrellaMapper umbrellaMapper;
    @Autowired
    private VolunteerService volunteerService;

    /**
     * 指定页码查询所有借取记录
     */
    @Test
    public void test01(){
        IPage<UmbrellaBorrow> iPage = umbrellaBorrowService.selectAll(1, 20);
        iPage.getRecords().forEach(System.out::println);
    }

    /**
     * 指定用户分页查询历史借阅雨伞记录
     */
//    @Test
//    public void test02(){
//        IPage<UmbrellaBorrow> umbrellaBorrowIPage = umbrellaBorrowService.selectList(null);
//        umbrellaBorrowIPage.getRecords().forEach(System.out::println);
//    }

    /**
     *
     */
    @Test
    public void test03(){
        QueryWrapper<UmbrellaBorrow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openID", "+7BfWy6IijmcZFO4Ac8fjmAvS8=");
        Page<UmbrellaBorrow> page = new Page<>();
        page.setCurrent(1).setSize(20);
        IPage<UmbrellaBorrow> umbrellaBorrowIPage = umbrellaMapper.selectPage(page, queryWrapper);
        umbrellaBorrowIPage.getRecords().forEach(System.out::println);
    }

    @Test
    public void TestBorrowByVolunteer(){
        Volunteer volunteer = volunteerService.getByOpenId("oR83j4kkq2CyvVmuxl6znKbrWi2A");
        Integer result = umbrellaBorrowService.borrowByVolunteer(volunteer);
        log.info("更新完的结果{}",result);
    }
    @Test
    public void TestReturnByVolunteer(){
        Volunteer volunteer = volunteerService.getByOpenId("oR83j4kkq2CyvVmuxl6znKbrWi2A");
        Integer result = umbrellaBorrowService.returnByVolunteer(volunteer);
        log.info("更新完的结果{}",result);
    }
}

