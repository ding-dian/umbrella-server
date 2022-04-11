
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.StorageClass;
import com.volunteer.VolunteerManagementApplication;
import com.volunteer.component.OSSOperator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 * @author: 梁峰源
 * @date: 2022/1/10 22:55
 * Description:
 */
@SpringBootTest(classes = VolunteerManagementApplication.class)
@RunWith(SpringRunner.class)
@Component
public class TestOOS {

    @Autowired
    OSSOperator ossOperator;

    String endpoint="oss-cn-beijing.aliyuncs.com";
    String accessKeyId="LTAI5tFfKdKLXjUE7wPgekaT";
    String accessKeySecret="S3Ubgng97xUNrt0xSw2cQUEpIVPxtL";
    String bucketName="lfy-hg-volunteer";
    String bucketDomain="https://lfy-hg-volunteer.oss-cn-beijing.aliyuncs.com/";


    /**
     * 官方示例代码-简单上传
     */
    @Test
    public void testOOS() {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        // 依次填写Bucket名称（例如examplebucket）、Object完整路径（例如exampledir/exampleobject.txt）和本地文件的完整路径。Object完整路径中不能包含Bucket名称。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
        PutObjectRequest putObjectRequest = new PutObjectRequest("lfy-hg-volunteer", "testUpload/testFile.png", new File("F:\\缓存资料\\qq缓存\\15_axios基础.png"));

        // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());

        // metadata.setObjectAcl(CannedAccessControlList.Private);
        putObjectRequest.setMetadata(metadata);

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void send(){
        System.out.println("测试");
        HttpResponse response = HttpRequest.post("http://localhost/volunteerActivity/updateStatus").execute();
        System.out.println(response.getStatus());
    }

//    @Test
//    public void testOSSOperator() throws FileNotFoundException {
//        File file = new File("F:\\缓存资料\\qq缓存\\15_axios基础.png");
//        // 指定文件名
//        FileInputStream fis1 = new FileInputStream(file);
//        System.out.println(ossOperator.uploadObjectOSS("utilTestFile/", UUID.randomUUID().toString(), file, fis1));
//        // 默认使用原文件名
//        FileInputStream fis2 = new FileInputStream(file);
//        System.out.println(ossOperator.uploadObjectOSS("utilTestFile/", file, fis2));
//    }
}
