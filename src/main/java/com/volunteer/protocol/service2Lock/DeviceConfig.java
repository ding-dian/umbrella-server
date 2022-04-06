package com.volunteer.protocol.service2Lock;

import com.volunteer.protocol.service2Lock.config.Config;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: 梁峰源
 * @date: 2022/4/2 22:47
 * Description:
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DeviceConfig implements Config {
    /**
     * 设置  Configuration item
     */
    private Config[] configs;
}
