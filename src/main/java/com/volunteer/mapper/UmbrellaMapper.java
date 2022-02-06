package com.volunteer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.volunteer.entity.Umbrella;
import com.volunteer.entity.UmbrellaBorrow;
import com.volunteer.entity.Volunteer;
import org.springframework.stereotype.Repository;

/**
 * @author: 梁峰源
 * @date: 2022/1/26 11:55
 * Description: 爱心雨伞数据库交互Mapper
 */
@Repository
public interface UmbrellaMapper extends BaseMapper<UmbrellaBorrow> {

}
