package com.volunteer.service;

import com.volunteer.entity.Volunteer;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    /**
     * 更新志愿者头像
     *
     * @param token
     * @param volunteer
     * @param avatarImg
     * @return
     */
    String uploadVolunteerAvatar(String token, Volunteer volunteer, MultipartFile avatarImg);
}

