import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.volunteer.VolunteerManagementApplication;
import com.volunteer.entity.Volunteer;
import com.volunteer.mapper.VolunteerMapper;
import com.volunteer.service.VolunteerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author VernHe
 * @date 2021年11月07日 19:38
 */
@SpringBootTest(classes = VolunteerManagementApplication.class)
@RunWith(SpringRunner.class)
public class TestClass {

    @Autowired
    private VolunteerMapper volunteerMapper;

    @Test
    public void test() {
        System.out.println(volunteerMapper.selectById(1));
    }
}
