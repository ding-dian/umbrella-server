package com.volunteer.entity.common;

import cn.hutool.http.HttpStatus;
import com.volunteer.util.Result;

/**
 * 响应结果生成工具
 * @author 何福任
 */

public class ResultGenerator {

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    /**
     * 只返回状态
     * @return
     */
    public static Result getSuccessResult() {
        return new Result()
                .setCode(HttpStatus.HTTP_OK)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * 成功返回数据
     * @param data
     * @return
     */
    public static Result getSuccessResult(Object data) {
        return new Result()
                .setCode(HttpStatus.HTTP_OK)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    /**
     * 失败
     * @param message
     * @return
     */
    public static Result getFailResult(String message) {
        return new Result()
                .setCode(HttpStatus.HTTP_BAD_REQUEST)
                .setMessage(message);
    }
}