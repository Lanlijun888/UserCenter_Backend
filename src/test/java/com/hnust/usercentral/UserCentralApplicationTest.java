package com.hnust.usercentral;

import com.examplespringbootstarter.ServiceBean;
import com.hnust.usercentral.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserCentralApplicationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceBean serviceBean;

    @Test
    public void test(){
        System.out.println(serviceBean.sayHello("Editor"));
    }

}