package com.volunteer.component;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author VernHe
 * @date 2021年11月29日 15:23
 */
//@Component
public class FastDFSClient {

    private final Logger logger = LoggerFactory.getLogger(FastDFSClient.class);

    /**
     * 支持的图片文件的后缀
     */
    private final String[] imgExt = new String[]{"jpg","png","gif","bmp","jpeg"};

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * 文件上传 (MultipartFile)
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return getResAccessUrl(storePath);
    }

    /**
     * 文件上传 (File)
     */
    public String uploadFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        StorePath path = storageClient.uploadFile(inputStream, file.length(),
                FilenameUtils.getExtension(file.getName()), null);
        return getResAccessUrl(path);
    }

    /**
     * 文件上传 (InputStream)
     */
    public String uploadFile(InputStream is, long size, String fileName) {
        StorePath path = storageClient.uploadFile(is, size, fileName, null);
        return getResAccessUrl(path);
    }

    /**
     * 将一段文本文件写到fastdfs的服务器上
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes( Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath path = storageClient.uploadFile(stream, buff.length, fileExtension, null);
        return getResAccessUrl(path);
    }

    /**
     * 上传图片
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public String uploadImageAndCrtThumbImage(File file) throws FileNotFoundException {
        if (isImageFile(file)) {
            FileInputStream inputStream = new FileInputStream(file);
            StorePath storePath = storageClient.uploadImageAndCrtThumbImage(inputStream, file.length(),
                    FilenameUtils.getExtension(file.getName()), null);
            return getResAccessUrl(storePath);
        }
        return null;
    }
    /**
     * 上传图片
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public String uploadImageAndCrtThumbImage(MultipartFile file) throws IOException {
        if (isImageFile(file)) {
            StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(),
                    FilenameUtils.getExtension(file.getOriginalFilename()), null);
            return getResAccessUrl(storePath);
        }
        return null;
    }

    /**
     * 是否为图片
     * @param file
     * @return
     */
    private boolean isImageFile(File file) {
        String ext = FilenameUtils.getExtension(file.getName());
        for (String str : imgExt) {
            if (StringUtils.equalsIgnoreCase(str,ext)) {
                return true;
            }
        }
        logger.info("文件后缀：【{}】,不是图片",ext);
        return false;
    }

    /**
     * 是否为图片
     * @param file
     * @return
     */
    private boolean isImageFile(MultipartFile file) {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        for (String str : imgExt) {
            if (StringUtils.equalsIgnoreCase(str,ext)) {
                return true;
            }
        }
        logger.info("文件后缀：【{}】,不是图片",ext);
        return false;
    }

    /**
     * 返回文件上传成功后的地址名称ַ
     */
    private String getResAccessUrl(StorePath storePath) {
        String fileUrl = storePath.getFullPath();
        return fileUrl;
    }

    /**
     * 下载文件 (文件url 文件路径)
     */
    public byte[] download(String fileUrl) {
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        byte[] bytes = storageClient.downloadFile(group, path, new DownloadByteArray());
        return bytes;
    }

    /**
     * 删除文件
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            logger.warn(e.getMessage());
        }
    }

    /**
     * 返回后缀名包含.
     */
    public String getSuffixName(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        return originalFilename.substring( originalFilename.lastIndexOf( "." ),originalFilename.length() );
    }

    /**
     * 返回文件名
     */
    public String getFileName(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        return originalFilename.substring( 0, originalFilename.lastIndexOf( "." ));
    }
}
