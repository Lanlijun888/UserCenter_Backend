package com.hnust.usercentral.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Editor
 */
@Data
public class UserLogin implements Serializable {

    private static final long serialVersionUID = -1140303279523337346L;
    private String userAccount;
    private String password;
}
