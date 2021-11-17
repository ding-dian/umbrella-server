import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.volunteer.VolunteerManagementApplication;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.vo.SignUpVo;
import com.volunteer.mapper.VolunteerMapper;
import com.volunteer.service.SignUpRecordService;
import com.volunteer.service.VolunteerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author VernHe
 * @date 2021年11月07日 19:38
 */
@SpringBootTest(classes = VolunteerManagementApplication.class)
@RunWith(SpringRunner.class)
public class TestClass {

    @Autowired
    private VolunteerMapper volunteerMapper;

    @Autowired
    private SignUpRecordService signUpRecordService;

    @Autowired
    private VolunteerService volunteerService;

    @Test
    public void test() {
        System.out.println(volunteerMapper.selectById(1));
    }

    @Test
    public void testSignUp() {
        SignUpVo query = new SignUpVo();
        query.setVolunteerId(1).setActivityId(1);

        System.out.println(signUpRecordService.signUp(query));
    }

    @Test
    public void testslectList() {
        Volunteer volunteer=new Volunteer();
        volunteer.setName("");
        volunteer.setId(2);
        volunteer.setGrade(18);
        volunteer.setPageNo(1);
        volunteer.setPageSize(3);
        System.out.println(volunteerService.selectList(volunteer));
    }

    @Test
    public void insertRecords() {
        for (int i = 0; i < 50; i++) {
            Volunteer volunteer = new Volunteer();
            Random random = new Random();
            volunteer.setName(random.nextInt(1000) + "");
            volunteer.setPassword(random.nextInt(999999) + "");
            volunteer.setCreateAt(LocalDateTime.now());
            volunteerMapper.insert(volunteer);
        }
    }
}
