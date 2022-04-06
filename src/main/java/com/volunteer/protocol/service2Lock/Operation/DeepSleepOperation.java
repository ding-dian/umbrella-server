package com.volunteer.protocol.service2Lock.Operation;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: 梁峰源
 * @date: 2022/4/3 0:02
 * Description:
 */
@Data
@AllArgsConstructor
public class DeepSleepOperation implements Operation{
    private Boolean is_deep_sleep;
}
