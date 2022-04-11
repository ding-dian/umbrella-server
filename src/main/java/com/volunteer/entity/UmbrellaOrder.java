package com.volunteer.entity;

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

/**
 * @author: 梁峰源
 * @date: 2022/2/8 14:42
 * Description: 雨伞借阅情况，存入redis中，当用户借阅雨伞时添加一条记录，归还时将记录删除
 */
@Data
@ApiModel(value = "雨伞借阅记录", description = "用来封装雨伞借阅情况")
@Accessors(chain = true)//开启链式编程
@EqualsAndHashCode(callSuper = false)
public class UmbrellaOrder implements Serializable {
    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1L;

    /**
     * 用户的openID
     */
    @ApiModelProperty(name = "openID", value = "用户的openID", required = true, example = "+7BfWy6IijmcZFO4Ac8fjmAvS8=")
    private String openID;

    /**
     * 用户的真实姓名
     */
    @ApiModelProperty(name = "userName", value = "用户的真实姓名", required = true, example = "梁峰源")
    private String userName;

    /**
     * 手机号
     */
    @ApiModelProperty(name="phoneNumber",value = "用户手机号码",required = true,example = "17670569756")
    private String phoneNumber;

    /**
     * qq号
     */
    @ApiModelProperty(name="qq",value = "qq号码",required = false,example = "2062728920")
    private String qqNumber;

    /**
     * 邮箱
     */
    @ApiModelProperty(name="emailAddress",value = "用户邮箱",required = true,example = "xxxx@foxmail.com")
    private String emailAddress;

    /**
     * 学号
     */
    @ApiModelProperty(name="studentId",value = "用户的学号",required = true ,example = "18020333222")
    private Long studentId;

    /**
     * 用户的借阅时间
     */
    @ApiModelProperty(name = "borrowDate", value = "用户的借阅时间", required = true, example = "2022-5-4 11:00:00")
    @DateTimeFormat(pattern = DataFormats.VO_FORMAT)//输入的时间格式
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DataFormats.VO_FORMAT, timezone = "GMT+8")//输出的时间格式，会自动转换
    private LocalDateTime borrowDate;

    /**
     * 用户借伞时长
     */
    @ApiModelProperty(name = "borrowDurations", value = "用户借伞时长", required = true, example = "4.5")
    private Double borrowDurations;
}
