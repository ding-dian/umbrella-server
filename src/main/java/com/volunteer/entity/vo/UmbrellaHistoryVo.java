package com.volunteer.entity.vo;

import com.volunteer.entity.UmbrellaHistoryBorrow;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZoneOffset;

/**
 * @author: 梁峰源
 * @date: 2022/2/12 0:19
 * Description: 爱心雨伞历史借阅信息
 */
@Data
@NoArgsConstructor
@ToString
public class UmbrellaHistoryVo {
    private Integer id;
    private String openID;
    private String userName;
    private String studentId;
    private long borrowDate;
    private long returnDate;
    private Double borrowDurations;
    private Integer borrowStatus;

    public UmbrellaHistoryVo(UmbrellaHistoryBorrow umbrellaHistoryBorrow) {
        this.id = umbrellaHistoryBorrow.getId();
        this.openID = umbrellaHistoryBorrow.getOpenID();
        this.userName = umbrellaHistoryBorrow.getUserName();
        this.studentId = umbrellaHistoryBorrow.getStudentId();
        this.borrowDate = umbrellaHistoryBorrow.getBorrowDate().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        this.returnDate = umbrellaHistoryBorrow.getReturnDate().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        this.borrowDurations = umbrellaHistoryBorrow.getBorrowDurations();
        this.borrowStatus = umbrellaHistoryBorrow.getBorrowStatus();
    }

}
