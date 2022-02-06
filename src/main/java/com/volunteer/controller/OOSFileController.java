package com.volunteer.controller;

import com.alibaba.druid.util.StringUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.volunteer.component.FastDFSClient;
import com.volunteer.component.OSSOperator;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.entity.common.VmProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;


/**
 * 阿里OOS文件和图片上传
 */
@Api(tags = "阿里OOS文件系统模块")
@RestController
@RequestMapping("/OOSfile")
@Slf4j
public class OOSFileController {
    @Autowired
    private OSSOperator ossOperator;
//    @Value("${oss.endpoint}")
//    private String endpoint;
//    @Value("${oos.accessKeyId}")
//    private String accessKeyId;
//    @Value("${oos.accessKeySecret}")
//    private String accessKeySecret;
//    @Value("${oos.bucketName}")
//    private String bucketName;
    String endpoint="oss-cn-beijing.aliyuncs.com";
    String accessKeyId="LTAI5tFfKdKLXjUE7wPgekaT";
    String accessKeySecret="S3Ubgng97xUNrt0xSw2cQUEpIVPxtL";
    String bucketName="lfy-hg-volunteer";
    String bucketDomain="https://lfy-hg-volunteer.oss-cn-beijing.aliyuncs.com/";

    @ApiOperation("图片上传接口")
    @PostMapping("/uploadImg")
    public Result uploadImgMethod(MultipartFile multipartFile) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream in = multipartFile.getInputStream();
            String fileName=multipartFile.getName();
            /*
             * getCurrentTimeStamp()方法为同步方法，确保时间戳的唯一性。
             */
            //时间戳
            String timeStamp = System.currentTimeMillis()+"";
            //获得年
            String timeDate = LocalDateTime.now().toString().split("-")[0];
            String key =timeDate + timeStamp +"/"+fileName;
            ossClient.putObject(new PutObjectRequest(bucketName, key, in));
            return ResultGenerator.getSuccessResult("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }


}
