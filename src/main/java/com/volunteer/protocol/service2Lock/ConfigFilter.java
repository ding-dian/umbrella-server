package com.volunteer.protocol.service2Lock;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.volunteer.protocol.service2Lock.config.Config;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 梁峰源
 * @date: 2022/4/2 23:19
 * Description: 如果字段为空则过滤掉无用的字段
 */
public class ConfigFilter {

    /**
     * 将对象转成json并删去无用字段
     */
    public static <T> JSON filter(T t){
        BeanMap beanMap = BeanMap.create(t);
        Map<String, Object> map = new HashMap<>();
        beanMap.forEach((k,v)->{
            //如果是配置类及其子类，递归调用
            if(v instanceof Config){
                map.put(String.valueOf(k),filter(v));
            }else if(!ObjectUtil.isNull(v) && !StringUtils.isEmpty(String.valueOf(v))){
                map.put(String.valueOf(k),v);
            }
        });
        return JSONUtil.parse(map);
    }
}
