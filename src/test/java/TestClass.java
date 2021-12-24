


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

//导入可选配置类
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;

// 导入对应SMS模块的client
import com.tencentcloudapi.sms.v20210111.SmsClient;

// 导入要请求接口对应的request response类
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.volunteer.VolunteerManagementApplication;
import com.volunteer.component.*;
import com.volunteer.entity.AdminInfo;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.VolunteerStatisticalInformation;
import com.volunteer.mapper.AdminInfoMapper;
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
    private AdminInfoMapper adminInfoMapper;
    @Autowired
    private VolunteerMapper volunteerMapper;

    @Autowired
    private SignUpRecordService signUpRecordService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private MiniProgramOperator miniProgramOperator;

    @Autowired
    private TencentSmsOperator smsOperator;

    @Autowired
    private SecretOperator secretOperator;

    @Test
    public void test() {
        System.out.println(volunteerMapper.selectById(1));
    }

    @Test
    public void testSignUp() {
//        SignUpVo query = new SignUpVo();
//        query.setVolunteerId(1).setActivityId(1);

//        System.out.println(signUpRecordService.signUp(query));
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
            String en = "admin";
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
        File file = new File("C:/Users/lenovo/Desktop/QQ截图20211130114207.png");
        // 上传普通文件
//        String uploadFile = fastDFSClient.uploadFile();
        // 上传图片带缩略图
        String uploadFile = fastDFSClient.uploadImageAndCrtThumbImage(file);
        System.out.println(uploadFile);

        // 删除
//        fastDFSClient.deleteFile("group1/M00/00/00/rBgar2GkgTuAF_qYABBlZbhgEOQ365.jpg");
    }
    @Test
    public void tests(){
//        AdminInfo adminInfo=adminInfoMapper.selectById(1);
//        adminInfo.setUsername("admin");
//        adminInfo.setPassword(AES.aesEncrypt("admin"));
//        adminInfoMapper.updateById(adminInfo);
        System.out.println(redisOperator.get("450c2bd0-6243-4c59-8501-34020b90b3a5"));
    }

    @Test
    public void testGetAccessToken() {
//        System.out.println(miniProgramOperator.getAccessTokenFromWx().toStringPretty());
        System.out.println(miniProgramOperator.getPhoneNumber("0713ZrFa1BFIiC0bvgHa1F8trz33ZrFP"));
    }

    /**
     * 测试发送短信
     */
    @Test
    public void testSMS() {
        try {
            /* 必要步骤：
             * 实例化一个认证对象，入参需要传入腾讯云账户密钥对secretId，secretKey。
             * 这里采用的是从环境变量读取的方式，需要在环境变量中先设置这两个值。
             * 你也可以直接在代码中写死密钥对，但是小心不要将代码复制、上传或者分享给他人，
             * 以免泄露密钥对危及你的财产安全。
             * CAM密匙查询: https://console.cloud.tencent.com/cam/capi*/
            Credential cred = new Credential("AKIDCDxRfcIhpgGZbNt86oFQKyKPDk0zg6G9", "e18yXprc6JFuIPFp8AvRgeLktlMgkX3I");

            // 实例化一个http选项，可选，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            // 设置代理
            // httpProfile.setProxyHost("真实代理ip");
            // httpProfile.setProxyPort(真实代理端口);
            /* SDK默认使用POST方法。
             * 如果你一定要使用GET方法，可以在这里设置。GET方法无法处理一些较大的请求 */
            httpProfile.setReqMethod("POST");
            /* SDK有默认的超时时间，非必要请不要进行调整
             * 如有需要请在代码中查阅以获取最新的默认值 */
            httpProfile.setConnTimeout(60);
            /* SDK会自动指定域名。通常是不需要特地指定域名的，但是如果你访问的是金融区的服务
             * 则必须手动指定域名，例如sms的上海金融区域名： sms.ap-shanghai-fsi.tencentcloudapi.com */
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            /* 非必要步骤:
             * 实例化一个客户端配置对象，可以指定超时时间等配置 */
            ClientProfile clientProfile = new ClientProfile();
            /* SDK默认用TC3-HMAC-SHA256进行签名
             * 非必要请不要修改这个字段 */
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            /* 实例化要请求产品(以sms为例)的client对象
             * 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，或者引用预设的常量 */
            SmsClient client = new SmsClient(cred, "ap-guangzhou",clientProfile);
            /* 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
             * 你可以直接查询SDK源码确定接口有哪些属性可以设置
             * 属性可能是基本类型，也可能引用了另一个数据结构
             * 推荐使用IDE进行开发，可以方便的跳转查阅各个接口和数据结构的文档说明 */
            SendSmsRequest req = new SendSmsRequest();

            /* 填充请求参数,这里request对象的成员变量即对应接口的入参
             * 你可以通过官网接口文档或跳转到request对象的定义处查看请求参数的定义
             * 基本类型的设置:
             * 帮助链接：
             * 短信控制台: https://console.cloud.tencent.com/smsv2
             * sms helper: https://cloud.tencent.com/document/product/382/3773 */

            /* 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId，示例如1400006666 */
            String sdkAppId = "1400613395";
            req.setSmsSdkAppId(sdkAppId);

            /* 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，签名信息可登录 [短信控制台] 查看 */
            String signName = "VernHe";
            req.setSignName(signName);

            /* 国际/港澳台短信 SenderId: 国内短信填空，默认未开通，如需开通请联系 [sms helper] */
            String senderid = "";
            req.setSenderId(senderid);

            /* 短信号码扩展号: 默认未开通，如需开通请联系 [sms helper] */
            String extendCode = "";
            req.setExtendCode(extendCode);

            /* 模板 ID: 必须填写已审核通过的模板 ID。模板ID可登录 [短信控制台] 查看 */
            String templateId = "1248012";
            req.setTemplateId(templateId);

            /* 下发手机号码，采用 E.164 标准，+[国家或地区码][手机号]
             * 示例如：+8613711112222， 其中前面有一个+号 ，86为国家码，15367985269为手机号，最多不要超过200个手机号 */
            String[] phoneNumberSet = {"+8615367985269"};
            req.setPhoneNumberSet(phoneNumberSet);

            /* 模板参数: 若无模板参数，则设置为空 */
            String[] templateParamSet = {"9999","3"};
            req.setTemplateParamSet(templateParamSet);

            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
            SendSmsResponse res = client.SendSms(req);

            // 输出json格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(res));

            // 也可以取出单个值，你可以通过官网接口文档或跳转到response对象的定义处查看返回字段的定义
            System.out.println(res.getRequestId());

        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOperator() throws Exception {
        System.out.println(smsOperator.sendSms("15367985269","123456"));
    }

    @Test
    public void testAES() {
        String s = secretOperator.aesEncrypt("17670459756");
        System.out.println(s);
        System.out.println(secretOperator.aesDecrypt(s));
    }
}
