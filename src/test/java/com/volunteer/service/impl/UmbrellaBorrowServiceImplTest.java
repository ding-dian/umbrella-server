package com.volunteer.service.impl;

import com.volunteer.VolunteerManagementApplication;
import com.volunteer.entity.UmbrellaHistoryBorrow;
import com.volunteer.entity.vo.UmbrellaHistoryVo;
import com.volunteer.service.UmbrellaBorrowService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * UmbrellaBorrowServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>2月 13, 2022</pre>
 */
@SpringBootTest(classes = VolunteerManagementApplication.class)
@RunWith(SpringRunner.class)
public class UmbrellaBorrowServiceImplTest {

    @Autowired
    private UmbrellaBorrowService umbrellaBorrowService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: selectHistoryList(Volunteer volunteer)
     */
    @Test
    public void testSelectHistoryList() throws Exception {
    //TODO: Test goes here...
    }

    /**
     * Method: selectHistoryAll(Integer pageNo, Integer pageSize)
     */
    @Test
    public void testSelectHistoryAll() throws Exception {


    }

    /**
     * Method: selectBorrow(Integer pageNo, Integer pageSize)
     */
    @Test
    public void testSelectBorrow() throws Exception {
    }

    /**
     * Method: selectOvertime(Integer pageNo, Integer pageSize)
     */
    @Test
    public void testSelectOvertime() throws Exception {
    //TODO: Test goes here...
    }

    /**
     * Method: borrowByVolunteer(Volunteer volunteer)
     */
    @Test
    public void testBorrowByVolunteer() throws Exception {

    }

    /**
     * Method: returnByVolunteer(Volunteer volunteer)
     */
    @Test
    public void testReturnByVolunteer() throws Exception {
    //TODO: Test goes here...
    }

    /**
     * Method: deleteOneRecordByVolunteer(UmbrellaHistoryBorrow umbrellaHistoryBorrow)
     */
    @Test
    public void testDeleteOneRecordByVolunteer() throws Exception {
    //TODO: Test goes here...
    }

    /**
     * Method: updateUmbrellaBorrowRecordByID(UmbrellaHistoryBorrow umbrellaHistoryBorrow)
     */
    @Test
    public void testUpdateUmbrellaBorrowRecordByID() throws Exception {
    //TODO: Test goes here...
    }

    /**
     * Method: updateUmbrellaStatus(Umbrella umbrella)
     */
    @Test
    public void testUpdateUmbrellaStatus() throws Exception {
    //TODO: Test goes here...
    }

    /**
     * Method: updateBorrowDurationJob()
     */
    @Test
    public void testUpdateBorrowDurationJob() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: borrowOvertimeHandle(Map<String,Double> overtimeUser)
     */
    @Test
    public void testBorrowOvertimeHandle() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: getBeanForRedis(String key, Integer pageNo, Integer pageSize)
     */
    @Test
    public void testGetBeanForRedis() throws Exception {
//TODO: Test goes here... 

    }

    /**
     * Method: parseRedisMap(String key, Object bean)
     */
    @Test
    public void testParseRedisMap() throws Exception {
//TODO: Test goes here... 

    }

    /**
     * Method: getAllGetMethod(Object bean)
     */
    @Test
    public void testGetAllGetMethod() throws Exception {
//TODO: Test goes here... 

    }

    /**
     * Method: getDuration(Object borrowDate)
     */
    @Test
    public void testGetDuration() throws Exception {
    //TODO: Test goes here...

    }

} 
