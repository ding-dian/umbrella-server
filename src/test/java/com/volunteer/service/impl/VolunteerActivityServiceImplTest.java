package com.volunteer.service.impl;

import com.volunteer.VolunteerManagementApplication;
import com.volunteer.entity.vo.ActivityListVo;
import com.volunteer.service.VolunteerActivityService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * VolunteerActivityServiceImpl Tester.
 *
 * @author fengxian
 * @version 1.0
 * @since <pre>02/20/2022</pre>
 */
@SpringBootTest(classes = VolunteerManagementApplication.class)
@RunWith(SpringRunner.class)
public class VolunteerActivityServiceImplTest {
    @Autowired
    private VolunteerActivityService  service;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: createActivity(VolunteerActivity volunteerActivity)
     */
    @Test
    public void testCreateActivity() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: deleteActivity(Integer id)
     */
    @Test
    public void testDeleteActivity() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: deleteListActivity(Integer[] ids)
     */
    @Test
    public void testDeleteListActivity() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: isAuditedActivity(AuditeActivityVo auditeActivity)
     */
    @Test
    public void testIsAuditedActivity() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: selectOne(Integer id)
     */
    @Test
    public void testSelectOne() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateActivity(VolunteerActivity volunteerActivity)
     */
    @Test
    public void testUpdateActivity() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: selectListActivity(VolunteerActivity volunteerActivity)
     */
    @Test
    public void testSelectListActivity() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: findListByStutas(String stutas, Integer pageNo, Integer pageSize)
     */
    @Test
    public void testFindListByStutas() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateActivityStatus(AuditeActivityVo auditeActivity)
     */
    @Test
    public void testUpdateActivityStatusAuditeActivity() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateActivityStatus()
     */
    @Test
    public void testUpdateActivityStatus() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: signIn(Integer volunteerId, Integer activityId)
     */
    @Test
    public void testSignIn() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: selectUserActivityList(Integer volunteerID, Integer pageNo, Integer pageSize)
     */
    @Test
    public void testSelectUserActivityList() throws Exception {
        ActivityListVo activityListVo = service.selectUserActivityList(111, 1, 20);
        activityListVo.getList()
                .forEach(System.out::println);
    }


    /**
     * Method: initStatus(VolunteerActivity volunteerActivity, LocalDateTime now)
     */
    @Test
    public void testInitStatus() throws Exception {
//TODO: Test goes here... 

    }

} 
