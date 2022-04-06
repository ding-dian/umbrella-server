package com.volunteer.protocol.service2Lock.Operation;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: 梁峰源
 * @date: 2022/4/2 23:34
 * Description:
 */
@Data
@AllArgsConstructor
public class AdsOperation implements Operation{
    private Boolean power;//电源开关
}
