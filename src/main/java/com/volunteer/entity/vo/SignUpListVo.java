package com.volunteer.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 最近报名列表展示的数据
 *
 * @author VernHe
 * @date 2021年12月25日 12:41
 */
@Data
public class SignUpListVo {

    /**
     * id
     */
    private Integer id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 报名日期
     */
    private String Date;

    /**
     * 是否签到【0:未签到，1:已签到】
     */
    private Integer isSignIn;
}
