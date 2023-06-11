package com.hnust.usercentral.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnust.usercentral.commons.ResultCodes;
import com.hnust.usercentral.constant.UserConstant;
import com.hnust.usercentral.exception.BusinessException;
import com.hnust.usercentral.model.domain.User;
import com.hnust.usercentral.service.UserService;
import com.hnust.usercentral.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

/**
* @author HUAWEI
* @deprecated  针对表【user(用户表)】的数据库操作Service实现
* @createDate 2023-05-19 12:34:23
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private UserMapper userMapper;
    private static final String SALT = "Editor";


    @Override
    public long userRegister(String userAccount, String password, String checkPassword,String planetCode) {
        //1、检验用户名，密码，确认密码是否符合条件
        if(StringUtils.isAnyBlank(userAccount,password,checkPassword,planetCode) || userAccount.length()<4 || password.length()<8 || planetCode.length() > 5){
            //判断用户名、密码、确认密码是否为空，如果有为空，则返回-1
            throw new BusinessException("Failed", ResultCodes.REGISTER_ERR,"基本信息不符合要求");
        }

        if(!password.equals(checkPassword)){
            //密码和确认密码要一致
            throw new BusinessException("Failed",ResultCodes.REGISTER_ERR,"两次输入密码不一致");
        }

        /*
            用户名的规则：
            用户名只能为英文字母,数字,下划线或者短横线组成, 并且用户名长度为4~16位.
        */
        if(!userAccount.matches("^[a-zA-Z0-9_-]{4,16}$")){
            throw new BusinessException("Failed",ResultCodes.REGISTER_ERR,"账户名不符合规则");
        }
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUserAccount,userAccount);
        User user = userMapper.selectOne(lqw);
        if(user != null){
            //账户不能重复
            throw new BusinessException("Failed",ResultCodes.REGISTER_ERR,"用户已存在");
        }

        LambdaQueryWrapper<User> lqw1 = new LambdaQueryWrapper();
        lqw1.eq(User::getPlanetCode,planetCode);
        User user1 = userMapper.selectOne(lqw1);
        if(user1 != null){
            throw new BusinessException("Failed",ResultCodes.REGISTER_ERR,"星球编号已存在，请重新输入！");
        }
        //经过以上的校验，基本校验完成
        //加密
        String newPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        //添加用户
        User userInsert = new User();
        userInsert.setUsername("AIHD_"+userAccount);
        userInsert.setUserAccount(userAccount);
        userInsert.setUserPassword(newPassword);
        userInsert.setPlanetCode(planetCode);
        userInsert.setCreateTime(new Date());
        userInsert.setUpdateTime(new Date());
        userInsert.setIsDelete(0);
        //此处返回的应该是影响的行数
        userMapper.insert(userInsert);
        return userInsert.getId();
    }

    @Override
    public User userLogin(String userAccount, String password, HttpServletRequest request) {
        //1、检验用户名，密码，确认密码是否符合条件
        if(StringUtils.isAnyBlank(userAccount,password) || userAccount.length()<4 || password.length()<8){
            //判断用户名、密码、确认密码是否为空，如果有为空，则返回-1
            throw new BusinessException("Failed",ResultCodes.LOGIN_ERR,"登录信息错误");
        }


        /*
            用户名的规则：
            用户名只能为英文字母,数字,下划线或者短横线组成, 并且用户名长度为4~16位.
        */
        if(!userAccount.matches("^[a-zA-Z0-9_-]{4,16}$")){
            //账户不能包含特殊字符
            throw new BusinessException("Failed",ResultCodes.LOGIN_ERR,"账户名不符合规则");
        }

        //经过以上的校验，基本校验完成
        //加密
        String newPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUserAccount,userAccount);
        lqw.eq(User::getUserPassword,newPassword);
        User user = userMapper.selectOne(lqw);

        if (user == null){
            log.info("user login:userAccount can not matcher password...");
            throw new BusinessException("Failed",ResultCodes.LOGIN_ERR,"用户不存在");
        }

        //用户名和密码均匹配成功，用户信息脱敏：返回数据之前要去除敏感信息，比如密码
        User newUser = getSafeUser(user);
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE,newUser);
        return newUser;
    }

    @Override
    public User getSafeUser(User user) {
        if (user == null){
            return null;
        }
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setUsername(user.getUsername());
        safeUser.setUserAccount(user.getUserAccount());
        safeUser.setAvatarUri(user.getAvatarUri());
        safeUser.setGender(user.getGender());
        safeUser.setPlanetCode(user.getPlanetCode());
        safeUser.setTel(user.getTel());
        safeUser.setEmail(user.getEmail());
        safeUser.setUserStatus(user.getUserStatus());
        safeUser.setCreateTime(user.getCreateTime());
        safeUser.setUserRole(user.getUserRole());
        return safeUser;
    }

    @Override
    public Integer userLogout(HttpServletRequest request) {
        //移除登录态
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return 1;
    }
}




