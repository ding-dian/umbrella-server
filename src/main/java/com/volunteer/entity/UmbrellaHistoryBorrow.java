package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.volunteer.entity.common.DataFormats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author: 梁峰源
 * @date: 2022/1/26 17:12
 * Description: 雨伞借阅历史记录
 */
@Data
@ApiModel(value = "雨伞借阅历史记录", description = "用来封装雨伞借阅情况")
@Accessors(chain = true)//开启链式编程
@EqualsAndHashCode(callSuper = false)
public class UmbrellaHistoryBorrow implements Serializable {
    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1L;
    /**
     * 雨伞借阅记录，索引
     */
    @ApiModelProperty(hidden = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 用户的openID
     */
    @ApiModelProperty(name = "openID", value = "用户的openID", required = true, example = "+7BfWy6IijmcZFO4Ac8fjmAvS8=")
    @TableField(value = "openid")
    private String openID;

    /**
     * 用户的姓名
     */
    @ApiModelProperty(name = "userName", value = "用户的姓名", required = true, example = "xxx")
    @TableField(value = "name",exist = false)
    private String userName;

    /**
     * 用户的学号
     */
    @ApiModelProperty(name = "studentId", value = "用户的学号", required = true, example = "xxx")
    @TableField(value = "student_id",exist = false)
    private String studentId;

    /**
     * 用户的借阅时间
     */
    @ApiModelProperty(name = "borrowDate", value = "用户的借阅时间", required = true, example = "2022-5-4 11:00:00")
    @DateTimeFormat(pattern = DataFormats.VO_FORMAT)//输入的时间格式
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DataFormats.VO_FORMAT, timezone = "GMT+8")//输出的时间格式，会自动转换
    @TableField("borrow_date")
    private LocalDateTime borrowDate;

    /**
     * 用户归还时间
     */
    @ApiModelProperty(name = "borrowDate", value = "用户的借阅时间", required = true, example = "2022-5-4 11:00:00")
    @DateTimeFormat(pattern = DataFormats.VO_FORMAT)//输入的时间格式
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DataFormats.VO_FORMAT, timezone = "GMT+8")//输出的时间格式，会自动转换
    @TableField("return_date")
    private LocalDateTime returnDate;

    /**
     * 用户借伞时长
     */
    @ApiModelProperty(name = "borrowDuration", value = "用户借伞时长", required = true, example = "4.5")
    @TableField("borrow_durations")
    private Double borrowDurations;

    /**
     * 该条记录的归还情况，0表示归还、1表示未归还
     */
    @ApiModelProperty(name = "borrowStatus", value = "用户归还状态，0表示已归还，1表示未归还", required = true, example = "0")
    @TableField(value = "borrow_status")
    private Integer borrowStatus;

    /**
     * 页号
     */
    @ApiModelProperty(hidden = true,name = "pageNo",value = "分页的页号",required = false,example = "1")
    @TableField(exist = false)
    private int pageNo;

    /**
     * 每页大小
     */
    @ApiModelProperty(hidden = true,name = "pageSize",value = "每页返回多少条数据",required = false,example = "20")
    @TableField(exist = false)
    private int pageSize;
}
