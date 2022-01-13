package com.volunteer.service.impl;

import cn.hutool.core.lang.UUID;
import com.volunteer.component.OSSOperator;
import com.volunteer.component.RedisOperator;
import com.volunteer.entity.Volunteer;
import com.volunteer.service.UploadService;
import com.volunteer.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadServiceImpl implements UploadService {

    static final String VOLUNTEER_STORE_PATH = "volunteer/";

    @Autowired
    private OSSOperator ossOperator;

    @Autowired
    private VolunteerService volunteerService;

    /**
     * 更新志愿者头像
     *
     * @param token
     * @param volunteer
     * @param avatarImg
     * @return
     */
    @Override
    public String uploadVolunteerAvatar(String token, Volunteer volunteer, MultipartFile avatarImg) {
        String fileName = UUID.fastUUID().toString(true);
        String url = ossOperator.uploadObjectOSS(VOLUNTEER_STORE_PATH, avatarImg, fileName);
        volunteerService.updateAvatar(token,volunteer,url);
        return url;
    }
}
