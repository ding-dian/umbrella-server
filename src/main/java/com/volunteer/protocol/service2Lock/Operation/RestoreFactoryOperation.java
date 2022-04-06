package com.volunteer.protocol.service2Lock.Operation;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: 梁峰源
 * @date: 2022/4/2 23:54
 * Description: 恢复出厂设置操作
 */
@Data
@AllArgsConstructor
public class RestoreFactoryOperation implements Operation{
    private Boolean is_restore_factory;//true恢复
}
