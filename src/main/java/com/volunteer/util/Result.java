package com.volunteer.util;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一API响应结果封装
 * @author 何福任
 */

@Data
public class Result implements Serializable {

    private int code;

    private String message = "success";

    private Object data;

    public Result setCode(int resultCode){
        this.code = resultCode;
        return this;
    }

    public Result setMessage(String message){
        this.message = message;
        return this;
    }

    public Result setData(Object data){
        this.data = data;
        return this;
    }

}