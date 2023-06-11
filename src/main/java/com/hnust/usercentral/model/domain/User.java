package com.hnust.usercentral.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
     */
    private String username;

    /**
     * 登录账号
     */
    private String userAccount;

    /**
     * 头像
     */
    private String avatarUri;

    /**
     * 性别

     */
    private Integer gender;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 电话号码
     */
    private String tel;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户的状态
     */
    private Integer userStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 用户角色，0表示普通用户，1表示管理员
     */
    private Integer userRole;

    /**
     * 星球编号
     */
    private String planetCode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}