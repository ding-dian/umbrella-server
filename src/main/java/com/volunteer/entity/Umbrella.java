package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author: 梁峰源
 * @date: 2022/1/26 18:26
 * Description: 爱心雨伞记录，记录爱心雨伞编号、借阅记录和描述
 */
@Data
@ApiModel(value = "爱心雨伞",description = "查看爱心雨伞编号、是否在库和描述等信息" )
public class Umbrella {
    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID=1L;

    @ApiModelProperty(name="id",value = "爱心雨伞的编号",required = true,example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(name="openID",value = "当前借取人的微信openID",required = true,example = "oR83j4kkq2CyvVmuxl6znKbrWi2A")
    @TableId("openID")
    private String openID;

    @ApiModelProperty(name="borrowDate",value = "当前借取人借取的时间",required = true,example = "2022年1月31日23:37:52")
    @TableId("borrowDate")
    private String borrowDate;

    @ApiModelProperty(name="status",value = "爱心雨伞在库情况描述，0表示在库、1表示不再库",example = "1")
    @TableField("status")
    private Integer status;
}
