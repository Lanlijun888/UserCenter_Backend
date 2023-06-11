package com.hnust.usercentral.commons;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Editor
 */
@Data
public class BaseResponse<T> implements Serializable {
    /**
     * 操作结果码
     */
    private String code;
    /**
     * 返回的数据
     */
    private T data;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 详细描述
     */
    private String description;

    /**
     * 有参无参构造函数
     */
    public BaseResponse(){}
    public BaseResponse(String code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(String code, T data) {
        this.code = code;
        this.data = data;
    }
}
