package com.hnust.usercentral.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnust.usercentral.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author HUAWEI
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-05-19 12:34:23
*/
public interface UserService extends IService<User> {

    /**
     *  注册用户
     * @param userAccount 昵称
     * @param password 密码
     * @param checkPassword 确认密码
     * @return 返回值
     */
    long userRegister(String userAccount,String password,String checkPassword,String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount 账户
     * @param password    密码
     * @param request
     * @return 用户实体类
     */
    User userLogin(String userAccount, String password, HttpServletRequest request);

    /**
     * 用户信息脱敏
     * @param user 用户对象
     * @return 安全用户信息
     */
    User getSafeUser(User user);

    /**
     * 用户注册
     * @param request   请求
     * @return integer
     */
    Integer userLogout(HttpServletRequest request);
}
