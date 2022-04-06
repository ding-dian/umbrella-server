package com.volunteer.protocol.service2Lock.Operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: 梁峰源
 * @date: 2022/4/2 23:24
 * Description: led灯操作
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LEDOperation implements Operation {
    /**
     * 电源开关<br>
     * <ul>
     *     <li>打开：true</li>
     *     <li>关闭：false</li>
     * </ul>
     */
    private Boolean power;
    /**
     * 亮度 0-100
     */
    private Integer brightness;
    /**
     * 三原色，传入{@code RGB}
     */
    private RGB rgb;
    //TODO 控制灯的闪烁

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RGB {
        private String red;
        private String green;
        private String blue;
    }
}
