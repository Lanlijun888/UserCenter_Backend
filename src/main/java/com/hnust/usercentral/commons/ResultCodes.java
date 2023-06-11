package com.hnust.usercentral.commons;

import lombok.Data;

/**
 * 操作结果码
 * @author Editor
 */
public class ResultCodes {
    public static final String NO_AUTH = "41000";
    public static final String LOGIN = "42001";
    public static final String UN_LOGIN = "42002";
    public static final String LOGOUT_SUCCESS = "42003";
    public static final String LOGOUT_ERR = "42004";
    public static final String REGISTER_SUCCESS = "40001";
    public static final String REGISTER_ERR = "40002";
    public static final String LOGIN_SUCCESS = "40003";
    public static final String LOGIN_ERR = "40004";
    public static final String SEARCH_SUCCESS = "40005";
    public static final String SEARCH_ERR = "40006";
    public static final String DELETE_SUCCESS = "40007";
    public static final String DELETE_ERR = "40008";

    public static final String BUSINESS_ERR = "50001";
    public static final String SYSTEM_ERR = "50002";


}
