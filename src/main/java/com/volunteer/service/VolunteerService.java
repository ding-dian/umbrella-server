package com.volunteer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.volunteer.entity.Volunteer;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;

import java.util.List;

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

}
