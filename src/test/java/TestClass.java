

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.tobato.fastdfs.service.GenerateStorageClient;
import com.volunteer.VolunteerManagementApplication;
import com.volunteer.component.FastDFSClient;
import com.volunteer.component.RedisOperator;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.VolunteerStatisticalInformation;
import com.volunteer.mapper.VolunteerStatisticalInformationMapper;
import com.volunteer.service.VolunteerStatisticalInformationService;
import com.volunteer.util.AES;
import com.volunteer.util.BeanMapUtil;
import com.volunteer.util.JwtUtil;

import com.volunteer.entity.vo.SignUpVo;
import com.volunteer.mapper.VolunteerMapper;
import com.volunteer.service.SignUpRecordService;
import com.volunteer.service.VolunteerService;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author VernHe
 * @date 2021年11月07日 19:38
 */
@SpringBootTest(classes = VolunteerManagementApplication.class)
@RunWith(SpringRunner.class)
public class TestClass {

    @Autowired
    private RedisOperator operator;

    @Autowired
    private VolunteerMapper volunteerMapper;

    @Autowired
    private SignUpRecordService signUpRecordService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private FastDFSClient fastDFSClient;

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

    @Test
    public void testEncrypt() throws Exception {
        {
            String en = "xiyangyang";
            String xx = AES.aesEncrypt(en);
            System.out.println(xx);
            String jm = AES.aesDecrypt(xx);
            System.out.println(jm);
            System.out.println("---------------");
            byte[] encrypt = AES.encrypt(en,AES.key);
            String decrypt = AES.decrypt(encrypt, AES.key);
            System.out.println(encrypt);
            System.out.println(decrypt);
        }
    }

    @Test
    public void testMapper() {
        volunteerMapper.selectTest(1);
    }

    @Test
    public void testRedisTemplate() {

        System.out.println(operator.get("test1"));
    }


    @Test
    public void  JwtUtil(){
        Volunteer volunteer =volunteerMapper.selectById(1);
        Map<String, Object> stringMap = BeanMapUtil.beanToMap(volunteer);
        String token = JwtUtil.generate(stringMap);
        System.out.println(token);
        System.out.println("claim:" + JwtUtil.getClaim(token).get("id"));
        System.out.println("header:" + JwtUtil.getHeader(token));
        //    System.out.println(getIssuedAt(token));
        Claims claims=JwtUtil.getClaim(token);

        //  System.out.println(getHeaderByBase64(token));
        System.out.println(JwtUtil.getPayloadByBase64(token));

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy‐MM‐dd hh:mm:ss");
       System.out.println("签发时间:"+sdf.format(claims.getIssuedAt()));
        System.out.println("过期时间:"+sdf.format(claims.getExpiration()));
       System.out.println("当前时间:"+sdf.format(new Date()) );

    }
    @Test
    public void updatePassword(){


        List<Volunteer> volunteers = volunteerMapper.selectList(new QueryWrapper<>());
//        for (int i=21;i<58;i++) {volunteerMapper.selectById(i);
//            Volunteer volunteer=
//            if (volunteer==null){
//                continue;
//            }
//            //VwuybRpgYLMP1NMcUdVyTg==
//            String password= AES.aesEncrypt(volunteer.getPassword());
//            volunteer.setPassword(password);
//            volunteerMapper.updateById(volunteer);
//        }
    }

//    @Test
//    public void xxx(){
//        VolunteerStatisticalInformation volunteerStatisticalInformation = volunteerStatisticalInformationMapper.selectByVolunteerId(1);
//        System.out.println(volunteerStatisticalInformation);
//    }

    @Test
    public void testFastDFS() throws IOException {
        File file = new File("C:/Users/Administrator/Desktop/截图/校园卡反面.jpg");
        // 上传普通文件
//        String uploadFile = fastDFSClient.uploadFile();
        // 上传图片带缩略图
        String uploadFile = fastDFSClient.uploadImageAndCrtThumbImage(file);
        System.out.println(uploadFile);

        // 删除
//        fastDFSClient.deleteFile("group1/M00/00/00/rBgar2GkgTuAF_qYABBlZbhgEOQ365.jpg");
    }
}
