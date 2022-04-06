package com.volunteer.protocol.service2Lock.Operation;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: 梁峰源
 * @date: 2022/4/2 23:58
 * Description: 重启操作
 */
@Data
@AllArgsConstructor
public class RestartOperation implements Operation{
    private boolean is_operation;
}
