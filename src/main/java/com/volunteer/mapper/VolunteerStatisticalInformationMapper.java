package com.volunteer.mapper;

import com.volunteer.entity.VolunteerStatisticalInformation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiaoyao
 * @since 2021-11-25
 */
public interface VolunteerStatisticalInformationMapper extends BaseMapper<VolunteerStatisticalInformation> {
        VolunteerStatisticalInformation selectByVolunteerId(@Param("volunteerId") Integer volunteerId);
}
