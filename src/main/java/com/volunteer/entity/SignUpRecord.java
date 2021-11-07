package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = false)
// 可以链式调用
@Accessors(chain = true)
public class SignUpRecord implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 报名记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 志愿者ID
     */
    private Integer volunteerId;

    /**
     * 活动ID
     */
    private Integer volunteerActivityId;

    /**
     * 逻辑删除【0:未删除，1:已删除】
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;


}
