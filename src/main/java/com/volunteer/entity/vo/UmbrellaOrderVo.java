package com.volunteer.entity.vo;

import com.volunteer.entity.UmbrellaOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author: 梁峰源
 * @date: 2022/2/12 0:08
 * Description:
 */
@Data
@NoArgsConstructor
public class UmbrellaOrderVo {
    private String openID;
    private String userName;
    private String phoneNumber;
    private String qqNumber;
    private String emailAddress;
    private Long studentId;
    private Long borrowDate;
    private Double borrowDurations;

    public UmbrellaOrderVo(UmbrellaOrder umbrellaOrder) {
        this.openID = umbrellaOrder.getOpenID();
        this.userName = umbrellaOrder.getUserName();
        this.phoneNumber = umbrellaOrder.getPhoneNumber();
        this.qqNumber = umbrellaOrder.getQqNumber();
        this.emailAddress = umbrellaOrder.getEmailAddress();
        this.studentId = umbrellaOrder.getStudentId();
        this.borrowDate = umbrellaOrder.getBorrowDate().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        this.borrowDurations = umbrellaOrder.getBorrowDurations();
    }
}
