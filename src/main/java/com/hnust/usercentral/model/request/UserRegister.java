package com.hnust.usercentral.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Editor
 */
@Data
public class UserRegister implements Serializable {
    private static final long serialVersionUID = 2557753849827811584L;
    private String userAccount;
    private String password;
    private String checkPassword;
    private String planetCode;
}
