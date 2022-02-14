package com.volunteer.util;

import com.volunteer.VolunteerManagementApplication;
import com.volunteer.service.MailService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * SendMailUtil Tester.
 *
 * @author fengxian
 * @version 1.0
 * @since <pre>02/14/2022</pre>
 */
@SpringBootTest(classes = VolunteerManagementApplication.class)
@RunWith(SpringRunner.class)
public class SendMailUtilTest {
    @Resource
    private SendMailUtil sendMailUtil;
    @Resource
    private MailService mailService;
    @Before
    public void before() throws Exception {

    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: sendOverTimeAlarm(String[] mailTo)
     */
    @Test
    public void testSendOverTimeAlarm() throws Exception {
        sendMailUtil.send2Admin("11111111");
    }

    /**
     * Method: send2Admin(String content)
     */
    @Test
    public void testSend2Admin() throws Exception {
        mailService.sendSimpleMail("2062728920@qq.com","奉先大大鸭",new String[]{"2062728920@qq.com"},
                null,"系统测试邮件","测试邮件请自动忽略");
    }


} 
