package com.volunteer.entity;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.volunteer.entity.common.DataFormats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
@Data
@Getter
@Setter
@ApiModel(value = "活动实体类",description = "接收活动参数的实体类")
@EqualsAndHashCode(callSuper = false)
public class VolunteerActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 志愿活动ID
     */
    @ApiModelProperty(name = "活动ID",required = false,example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 活动名称
     */
    @ApiModelProperty(name = "activityName",value = "活动名称",required = true,example = "2022年第一次巡河净滩活动")
    @TableField("activity_name")
    private String activityName;

    /**
     * 活动图片
     */
    @ApiModelProperty(name = "activityImg",value = "活动展示图片的url地址",required = true,example = "https://img1.baidu.com/it/u=3427821080,1838386749&fm=26&fmt=auto")
    @TableField("activity_img")
    private String activityImg;

    /**
     * 活动地点
     */
    @ApiModelProperty(name = "activityAddress",value = "活动地点",required = true,example = "衡阳市春天之家福利院")
    @TableField("activity_address")
    private String activityAddress;

    /**
     * 活动所需人数
     */
    @ApiModelProperty(name = "numberOfNeed",value = "活动所需人数",required = true,example = "20")
    @TableField("number_of_need")
    private Integer numberOfNeed;

    /**
     * 活动参加人数
     */
    @ApiModelProperty(name = "numberOfAttendees",value = "活动已经报名的人数",required = true,example = "0")
    @TableField("number_of_attendees")
    private Integer numberOfAttendees;

    /**
     * 活动描述
     */
    @ApiModelProperty(name = "description",value = "活动描述",required = true,example = "这是一次很有意义的活动")
    @TableField("description")
    private String description;

    /**
     * 奖励积分
     */
    @ApiModelProperty(name = "rewardPoints",value = "活动奖励的志愿时长",required = true,example = "4")
    @TableField("reward_points")
    private Integer rewardPoints;

    /**
     * 活动开始时间
     */
    @ApiModelProperty(name = "startTime",value = "活动开始时间",required = true,example = "2022-03-15 13:00")
    @TableField("start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = DataFormats.VO_FORMAT)
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    @ApiModelProperty(name = "endTime",value = "活动结束时间",required = true,example = "2022-03-15 17:00")
    @TableField("end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = DataFormats.VO_FORMAT)
    private LocalDateTime endTime;

    /**
     * 状态【00:进行中，01:未开始，02:已结束】
     */
    @ApiModelProperty(name = "活动所处状态",value = "状态【00:进行中，01:未开始，02:已结束】",required = true,example = "01")
    @TableField("status")
    private String status;


    /**
     * 逻辑删除【0:未删除，1:已删除】
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField("create_at")
    private LocalDateTime createAt;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 更新时间
     */
    @TableField("update_at")
    private LocalDateTime updateAt;

    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 【0:未审核，1:已审核】
     */
    @TableField("is_audited")
    private Integer isAudited;

    /**
     * 预计志愿活动时长
     */
    @TableField("predict_duration")
    private Double predictDuration;

    /**
     * 实际志愿活动时长
     */
    @TableField("actual_duration")
    private Double actualDuration;

    /**
     * 联系人
     */
    @TableField("contact_name")
    private String contactName;

    /**
     * 联系电话
     */
    @TableField("contact_number")
    private String contactNumber;

    /**
     * 页号
     */
    @TableField(exist = false)
    private int pageNo;

    /**
     * 每页大小
     */
    @TableField(exist = false)
    private int pageSize;

    public void setStartTime(String startTime) {
        this.startTime = LocalDateTimeUtil.parse(startTime, DataFormats.VO_FORMAT);
    }

    public void setEndTime(String endTime) {
        this.endTime = LocalDateTimeUtil.parse(endTime, DataFormats.VO_FORMAT);
    }

    public VolunteerActivity() {
    }
}
