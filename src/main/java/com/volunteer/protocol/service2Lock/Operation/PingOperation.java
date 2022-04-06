package com.volunteer.protocol.service2Lock.Operation;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: 梁峰源
 * @date: 2022/4/2 23:34
 * Description:
 */
@Data
public class PingOperation implements Operation{
    private Boolean ping = true;//电源开关
}
