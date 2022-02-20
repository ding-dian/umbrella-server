package com.volunteer.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: 梁峰源
 * @date: 2022/2/18 19:45
 * Description:
 */
@Data
@Accessors(chain = true)//开启链式编程
public class UmbrellaHistoryListVo {
    /**
     * 第几页
     */
    private Integer pageNo;
    /**
     * 显示多少条数据
     */
    private Integer pageSize;
    /**
     * 共多少条数据
     */
    private Integer Total;
    /**
     * 返回的数据集合
     */
    private List<UmbrellaHistoryVo> records;
}
