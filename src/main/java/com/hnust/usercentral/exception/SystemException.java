package com.hnust.usercentral.exception;

import lombok.Data;

/**
 * 系统服务异常
 * @author Editor
 */
@Data
public class SystemException extends RuntimeException{
    private String code;
    private String description;

    public SystemException(String message, String code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }
}
