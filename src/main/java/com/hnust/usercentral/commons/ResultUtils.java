package com.hnust.usercentral.commons;

/**
 * @author Editor
 */
public class ResultUtils {
    public static <T> BaseResponse<T> success(String code,T data){
        return new BaseResponse<>(code,data,"Successful","successful");
    }

    public static <T> BaseResponse<T> error(String code,T data,String description){
        return new BaseResponse<>(code,data,"Failed",description);
    }
}
