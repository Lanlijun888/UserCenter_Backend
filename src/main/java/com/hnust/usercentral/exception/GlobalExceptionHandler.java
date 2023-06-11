package com.hnust.usercentral.exception;

import com.hnust.usercentral.commons.BaseResponse;
import com.hnust.usercentral.commons.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Editor
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public BaseResponse BusinessExceptionHandler(BusinessException exception){
        return ResultUtils.error(exception.getCode(),null,exception.getDescription());
    }

    @ExceptionHandler
    public BaseResponse SystemExceptionHandler(SystemException exception){
        return ResultUtils.error(exception.getCode(),null,exception.getDescription());
    }
}
