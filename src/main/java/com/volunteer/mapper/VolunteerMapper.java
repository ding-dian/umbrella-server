package com.volunteer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.volunteer.entity.Volunteer;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
public interface VolunteerMapper extends BaseMapper<Volunteer> {
    Volunteer selectTest(int id);

    /**
     * 根据OpenId查询志愿者信息
     * @param openid
     * @return
     */
    Volunteer selectByOpenid(String openid);
}
