package com.volunteer.protocol;

/**
 * @author: 梁峰源
 * @date: 2022/4/2 20:32
 * Description: 设备状态字典，锁机里面的设备以2开头,int32类型
 */
//@ApiModel(value = "锁机", description = "用来封装雨伞借阅情况")
public class LockDeviceDic {
    /**
     * 锁机自身 {@code 2001}
     */
    public static final Integer SELF_CODE = 2001;
    public static final String SELF = "self";
    /**
     * 灯 {@code 2002}
     */
    public static final Integer LED_CODE  = 2002;
    public static final String LED  = "LED_lamp";
    /**
     * 广告屏 {@code 2003}
     */
    public static final Integer ADS_CODE  = 2003;
    public static final String ADS  = "advertising_screen";
    /**
     * 电子锁 {@code 2004}
     */
    public static final Integer EL_LOCK_CODE  = 2004;
    public static final String EL_LOCK  = "electronic_lock";
    /**
     * 设置wifi {@code 2005}
     */
    public static final Integer WIFI_CODE  = 2005;
    public static final String WIFI  = "wifi";

    /**
     * 枚举
     */
    public enum PostType{
        test,control,config
    }
}
