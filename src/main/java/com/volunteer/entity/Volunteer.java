package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
@Data
@ApiModel(value = "志愿者对象",description = "用来接收志愿者信息的实体" )
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Volunteer implements Serializable {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID=1L;

    /**
     * 志愿者ID
     */
    @ApiModelProperty(hidden = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户在小程序端唯一的ID
     */
    @ApiModelProperty(name="openid",value = "用户在小程序端唯一的ID", required = true,example = "oVBfcx4efaRhA4iQo+7BfWy6IijmcZFO4Ac8fjmAvS8=")
    private String openid;

    /**
     * 姓名
     */
    @ApiModelProperty(name="name",value = "用户真实姓名",required = true,example = "佩奇")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty(name="phoneNumber",value = "用户手机号码",required = true,example = "123456")
    private String phoneNumber;

    /**
     * 邮箱
     */
    @ApiModelProperty(name="emailAddress",value = "用户邮箱",required = false,example = "xxxx@foxmail.com")
    private String emailAddress;

    /**
     * 学号
     */
    @ApiModelProperty(name="studentId",value = "用户的学号",required = true ,example = "18020333222")
    private Long studentId;

    /**
     * 密码
     */
    @ApiModelProperty(name="password",value = "用户密码",required = false,example = "123456")
    private String password;

    /**
     * 学院
     */
    @ApiModelProperty(name="institude",value = "用户所在学院",required = false,example = "计信")
    private String institude;

    /**
     * 年级
     */
    @ApiModelProperty(name="grade",value = "用户所在年纪",required = false,example = "2")
    private Integer grade;

    /**
     * 专业
     */
    @ApiModelProperty(name="major",value = "用户的专业",required = false,example = "网络")
    private String major;

    /**
     * 昵称
     */
    @ApiModelProperty(name="nickName",value = "用户微信昵称",required = true,example = "昵称")
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 性别
     */
    @ApiModelProperty(required = false,notes = "【0:未知,1:男,2:女】",example = "1")
    private Integer gender;

    /**
     * 头像
     */
    @ApiModelProperty(name="avatarUrl",value = "用户头像地址",required = true,example = "https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eplWzPJuvFyWeY5KjG5mOv6a7YNHNDvyxCibSCv58iaxicjyjPD08FoicQDBibMoCF64urOjYFEicM5KTeQ/132")
    private String avatarUrl;


    /**
     * 逻辑删除【0:未删除，1:已删除】
     */
    @TableLogic
    @ApiModelProperty(hidden = true)
    private Integer deleted;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime createAt;

    /**
     * 创建人
     */
    @ApiModelProperty(hidden = true)
    private String createBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime updateAt;

    /**
     * 更新人
     */
    @ApiModelProperty(hidden = true)
    private String updateBy;

    /**
     * 页号
     */
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private int pageNo;

    /**
     * 每页大小
     */
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private int pageSize;

    /**
     * 用户参加志愿活动总时长
     */
    @ApiModelProperty(name="activityTotaltime",value = "用户参加志愿活动的总时长",notes="不保存小数，向上取整",required = true,example = "4")
//    @TableField("activityTotaltime")
    @TableField(exist = false)
    private Double activityTotaltime;

    /**
     * 用户参加志愿活动总次数
     */
    @ApiModelProperty(name="activityNumber",value = "用户参加志愿活动的总次数",required = true,example = "4")
//    @TableField("activityNumber")
    @TableField(exist = false)
    private Integer activityNumber;

    /**
     * 用户借取爱心雨伞的时间
     */
    @ApiModelProperty(name="borrowUmbrellaDate",value = "用户借取爱心雨伞的时间",required = true,example = "2022年2月1日00:27:56")
//    @TableField("borrowUmbrellaDate")
    @TableField(exist = false)
    private LocalDateTime borrowUmbrellaDate;


}
