package com.volunteer.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
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

    /**
     * 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
     * @param updateWrapper
     * @return
     */
    boolean update(Wrapper<Volunteer> updateWrapper);

    /**
     * 根据姓名查询志愿者
     * @param name
     * @return
     */
    Volunteer selectByName(String name);
}
