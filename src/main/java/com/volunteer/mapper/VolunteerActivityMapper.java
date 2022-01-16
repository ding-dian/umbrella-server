package com.volunteer.mapper;

import com.volunteer.entity.VolunteerActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
public interface VolunteerActivityMapper extends BaseMapper<VolunteerActivity> {
    /**
     * 批量更新活动状态
     * @param list
     * @return
     */
    int updateStatus(List<VolunteerActivity> list);
}
