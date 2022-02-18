package com.volunteer.component;

import com.volunteer.VolunteerManagementApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 * OSSOperator Tester.
 *
 * @author fengxian
 * @version 1.0
 * @since <pre>02/15/2022</pre>
 */
@SpringBootTest(classes = VolunteerManagementApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class OSSOperatorTest {

    @Autowired
    private OSSOperator ossOperator;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: afterPropertiesSet()
     */
    @Test
    public void testAfterPropertiesSet() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: deleteBucket(String bucketName)
     */
    @Test
    public void testDeleteBucket() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: deleteFile(String bucketName, String folder, String key)
     */
    @Test
    public void testDeleteFile() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: uploadObjectOSS(String storePath, File file)
     */
    @Test
    public void testUploadObjectOSSForStorePathFile() {
        File file = new File("E:\\计信青协2020-2021文件\\服务器放图片\\2.jpg");
        String url = ossOperator.uploadObjectOSS("volunteer/", file);
        log.info("图片路径：{}",url);
    }

    /**
     * Method: uploadObjectOSS(String storePath, File file, String fileName)
     */
    @Test
    public void testUploadObjectOSSForStorePathFileFileName() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: setEndpoint(String endpoint)
     */
    @Test
    public void testSetEndpoint() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: setAccessKeyId(String accessKeyId)
     */
    @Test
    public void testSetAccessKeyId() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: setAccessKeySecret(String accessKeySecret)
     */
    @Test
    public void testSetAccessKeySecret() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: setBucketName(String bucketName)
     */
    @Test
    public void testSetBucketName() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: setBucketDomain(String bucketDomain)
     */
    @Test
    public void testSetBucketDomain() throws Exception {
        //TODO: Test goes here...
    }


    /**
     * Method: createBucketName(String bucketName)
     */
    @Test
    public void testCreateBucketName() throws Exception {

    }

    /**
     * Method: createFolder(String bucketName, String folder)
     */
    @Test
    public void testCreateFolder() throws Exception {

    }

    /**
     * Method: upload(String storePath, Object file, String fileName, String extName)
     */
    @Test
    public void testUpload() throws Exception {

    }

    /**
     * Method: getInputStream(Object file)
     */
    @Test
    public void testGetInputStream() throws Exception {

    }

    /**
     * Method: getFileSize(Object file)
     */
    @Test
    public void testGetFileSize() throws Exception {

    }

    /**
     * Method: getContentType(Object file)
     */
    @Test
    public void testGetContentType() throws Exception {


    }

} 
