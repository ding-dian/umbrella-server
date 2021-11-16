package com.volunteer.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.volunteer.entity.Volunteer;
import com.volunteer.mapper.VolunteerMapper;
import com.volunteer.service.VolunteerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
@Service
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, Volunteer> implements VolunteerService {

    @Autowired
    VolunteerMapper volunteerMapper;

    @Override
    public ResponseEntity<String> register(Volunteer register) {

        if (register.getName()!=null||register.getPassword()!=null||register.getPhoneNumber()!=null){
            if (ObjectUtil.isNotNull(register)){
                return ResponseEntity.badRequest().body("账号已被注册！");
            }
            volunteerMapper.insert(register);
        }else {
            ResponseEntity.badRequest().body("请检查账号，密码和手机号是否为空！");
        }
        return ResponseEntity.badRequest().body("注册成功");
    }

}

