package com.volunteer.mapper;

import com.volunteer.entity.AdminInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiaoyao
 * @since 2021-12-06
 */
public interface AdminInfoMapper extends BaseMapper<AdminInfo> {
    AdminInfo selectByUserName(String userName);
}
