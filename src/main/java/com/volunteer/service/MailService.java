package com.volunteer.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author: 梁峰源
 * @date: 2022年2月13日20:18:28
 * Description: 发送邮件接口
 */
@Service
public interface MailService {

    void sendSimpleMail(String mailFrom, String mailFromNick, String[] mailTo, String cc, String subject, String content);

    void sendMailWithAttachments(String mailFrom, String mailFromNick, String mailTo, String cc, String subject, String content,
                                 List<File> files);

    void sendMailWithImage(String mailFrom, String mailFromNick, String mailTo, String cc, String subject, String content,
                           String[] imagePaths, String[] imageId);

    void sendHtmlMailThymeLeaf(String mailFrom, String mailFromNick, String mailTo, String cc, String subject, String content);
}
