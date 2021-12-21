package com.volunteer.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.volunteer.entity.Volunteer;
import com.baomidou.mybatisplus.extension.service.IService;

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
    int register(Volunteer register);

    /**
     * 根据条件查询志愿者列表
     *
     * @param volunteer
     * @return
     */
    IPage<Volunteer> selectList(Volunteer volunteer);

    /**
     * 删除一个志愿者
     * @param id
     * @return
     */
    int deleteVolunteer(Integer id);

    /**
     * 批量删除志愿者
     *
     * @param ids
     * @return
     */
    void deleteList(Integer[] ids);

    /**
     * 查询单个志愿者
     *
     * @param id
     * @return
     */
    Volunteer selectOne(Integer id);

    /**
     * 更新志愿者信息
     * @param volunteer
     * @return
     */
    int update(Volunteer volunteer);

    /**
     * 根据OpenId获取志愿者信息
     * @param openId
     * @return
     */
    Volunteer getByOpenId(String openId);

    /**
     * 解析jsonObject并存入数据库
     * @param jsonObject
     * @return
     */
    Volunteer register(JSONObject jsonObject);

    /**
     * 检查手机号是否已经被绑定
     * @param phoneNumber
     * @return
     */
    boolean phoneNumberIsBound(String phoneNumber);

}
