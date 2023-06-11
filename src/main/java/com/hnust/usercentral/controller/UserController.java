package com.hnust.usercentral.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hnust.usercentral.commons.BaseResponse;
import com.hnust.usercentral.commons.ResultCodes;
import com.hnust.usercentral.commons.ResultUtils;
import com.hnust.usercentral.constant.UserConstant;
import com.hnust.usercentral.model.domain.User;
import com.hnust.usercentral.model.request.UserLogin;
import com.hnust.usercentral.model.request.UserRegister;
import com.hnust.usercentral.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Editor
 */
@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    BaseResponse userRegister(@RequestBody UserRegister userRegister){
        if (userRegister == null){
            return ResultUtils.error(ResultCodes.REGISTER_ERR,null,"请求参数为空，注册失败");
        }
        //非空校验
        String userAccount = userRegister.getUserAccount();
        String password = userRegister.getPassword();
        String checkPassword = userRegister.getCheckPassword();
        String planetCode = userRegister.getPlanetCode();
        if(StringUtils.isAnyBlank(userAccount,password,checkPassword,planetCode)){
            return ResultUtils.error(ResultCodes.REGISTER_ERR,null,"注册信息不完整，注册失败");
        }
        long register = userService.userRegister(userAccount, password, checkPassword,planetCode);
        return ResultUtils.success(ResultCodes.REGISTER_SUCCESS,register);
    }

    @PostMapping("/login")
    BaseResponse userLogin(@RequestBody UserLogin userLogin, HttpServletRequest request){
        if (userLogin == null){
            return ResultUtils.error(ResultCodes.LOGIN_ERR,null,"请求参数为空，登录失败");
        }
        String userAccount = userLogin.getUserAccount();
        String password = userLogin.getPassword();
        if (StringUtils.isAnyBlank(userAccount,password)){
            return ResultUtils.error(ResultCodes.LOGIN_ERR,null,"登录信息不完整，登录失败");
        }
        User user = userService.userLogin(userAccount, password, request);
        return user!=null ? ResultUtils.success(ResultCodes.LOGIN_SUCCESS,user) : ResultUtils.error(ResultCodes.LOGIN_ERR,null,"用户不存在");
    }

    @GetMapping("/search")
    BaseResponse userSearch(String userAccount,HttpServletRequest request){
        if (!isAdmin(request)){
            return ResultUtils.error(ResultCodes.NO_AUTH,null,"权限不足");
        }
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.like(userAccount!=null,User::getUserAccount,userAccount);
        List<User> userList = userService.list(lqw);
        return ResultUtils.success(ResultCodes.SEARCH_SUCCESS,
                userList.stream().map(user -> {
                    user.setUserPassword(null);
                    return user;
                }).collect(Collectors.toList()));
    }

    @PostMapping("/delete/{id}")
    BaseResponse userDelete(@PathVariable long id,HttpServletRequest request){
        if (!isAdmin(request)){
            return ResultUtils.error(ResultCodes.NO_AUTH,null,"权限不足");
        }
        if (id <= 0){
            return ResultUtils.error(ResultCodes.DELETE_ERR,null,"参数错误，删除失败");
        }
        boolean remove = userService.removeById(id);
        return remove ? ResultUtils.success(ResultCodes.DELETE_SUCCESS,null) : ResultUtils.error(ResultCodes.DELETE_ERR,null,"删除失败");
    }

    @GetMapping("/currentUser")
    BaseResponse getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if(currentUser == null){
            return ResultUtils.error(ResultCodes.UN_LOGIN,null,"请先登录");
        }
        //因为数据的信息会更新，所有拿到用户信息，还要查一次数据库，获取最新信息
        Long userId = currentUser.getId();
        User userServiceById = userService.getById(userId);
        //数据脱敏操作
        User user = userService.getSafeUser(userServiceById);
        return ResultUtils.success(ResultCodes.LOGIN,user);
    }

    boolean isAdmin(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user = (User) userObj;
        if(user == null || user.getUserRole()!=UserConstant.ADMIN_ROLE){
            return false;
        }
        return true;
    }

    @PostMapping("/logout")
    BaseResponse userLogout(HttpServletRequest request){
        if(request == null){
            return ResultUtils.error(ResultCodes.LOGOUT_ERR,null,"请求参数为空，注销失败");
        }
        return ResultUtils.success(ResultCodes.LOGOUT_SUCCESS,null);
    }
}
