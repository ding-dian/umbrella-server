package com.volunteer.service;

import com.volunteer.entity.Volunteer;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
public interface VolunteerService extends IService<Volunteer> {
    /**
     * 志愿者注册
     *
     * @return
     */
    ResponseEntity<String> register(Volunteer register);
}
