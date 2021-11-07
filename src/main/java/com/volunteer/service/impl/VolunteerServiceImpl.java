package com.volunteer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.volunteer.entity.SignUpRecord;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.vo.SignUpVo;
import com.volunteer.mapper.VolunteerMapper;
import com.volunteer.service.VolunteerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
