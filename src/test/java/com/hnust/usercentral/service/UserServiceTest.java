package com.hnust.usercentral.service;
import java.util.Date;

import com.hnust.usercentral.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void testInsert(){
        User user = new User();
        user.setUsername("Editor");
        user.setUserAccount("EditorAccount");
        user.setAvatarUri("www.baidu.com");
        user.setGender(0);
        user.setUserPassword("123456");
        user.setTel("12345678");
        user.setEmail("3143826150");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);

        userService.save(user);
        System.out.println(user.getId());
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"/userData.csv"})
    void testUserRegister(String userAccount,String password,String checkPassword,String planetCode){
        System.out.println(userAccount);
        System.out.println(password);
        System.out.println(checkPassword);
        long l = userService.userRegister(userAccount,password,checkPassword,planetCode);
        Assertions.assertEquals(1,l);
    }

    @Test
    void userInsertTest(){
        long register = userService.userRegister("123456", "123456789", "123456789","123");
        if (register > 0){
            System.out.println("注册成功！");
        }else {
            System.out.println("注册失败！");
        }
    }
}