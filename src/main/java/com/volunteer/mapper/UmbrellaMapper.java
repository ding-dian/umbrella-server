package com.volunteer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.volunteer.entity.UmbrellaHistoryBorrow;
import com.volunteer.entity.UmbrellaOrder;

import java.util.List;

/**
 * @author: 梁峰源
 * @date: 2022/1/26 11:55
 * Description: 爱心雨伞数据库交互Mapper
 */
public interface UmbrellaMapper extends BaseMapper<UmbrellaHistoryBorrow> {

    /**
     * 查询volunteer、umbrella_history_borrow表，返回用户借伞的历史数据
     * @param skip 跳过数据数
     * @param pageSize 一页返回多少条数据
     * @return 返回查询到的集合
     */
    List<UmbrellaHistoryBorrow> selectHistoryAll(int skip,int pageSize);

    /**
     * 查询数据库中数据条数
     * @return 数据条数
     */
    Integer selectCount();
}
