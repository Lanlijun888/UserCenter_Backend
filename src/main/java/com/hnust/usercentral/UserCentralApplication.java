package com.hnust.usercentral;

import com.examplespringbootstarter.EnableAutoConfigTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfigTest
@SpringBootApplication
public class UserCentralApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCentralApplication.class, args);
    }

}
