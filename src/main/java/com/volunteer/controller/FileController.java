package com.volunteer.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.druid.util.StringUtils;
import com.volunteer.component.FastDFSClient;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.entity.common.VmProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * 文件和图片上传
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {
    @Autowired
    private FastDFSClient fastDFSClient;

    @PostMapping("/uploadImg")
    @ResponseBody
    public Result uploadImgMethod(MultipartFile file) {

        try {
            String path = fastDFSClient.uploadImageAndCrtThumbImage(file);
            return getPath(path,"你上传的不是图片");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }

    @PostMapping("/uploadFile")
    @ResponseBody
    public Result uploadFileMethod(MultipartFile file) {

        try {
            log.info("文件名：【{}】",file.getOriginalFilename());
            String path = fastDFSClient.uploadFile(file);
            return getPath(path, "文件上传失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getFailResult(e.getMessage());
        }
    }

    private Result getPath(String path, String errMsg) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!StringUtils.isEmpty(path)) {
            StringBuilder fullpath = stringBuilder.append(VmProperties.HTTP_PROTOCOL)
                    .append(VmProperties.VM_HOST)
                    .append(VmProperties.COLON)
                    .append(VmProperties.VM_NGINX_PORT)
                    .append(VmProperties.BACKSLASH)
                    .append(path);
            return ResultGenerator.getSuccessResult(fullpath);
        } else {
            return ResultGenerator.getFailResult(errMsg);
        }
    }


}
