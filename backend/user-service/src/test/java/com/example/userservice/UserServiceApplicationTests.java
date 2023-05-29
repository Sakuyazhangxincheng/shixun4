package com.example.userservice;

import com.example.userservice.service.EmailService;
import com.example.userservice.service.UserService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() throws MessagingException {
        emailService.sendEmailVerification("911124552@qq.com");
        //userService.register("TestStudent1", "20230001", "911124552@qq.com", "myq888", "1234", "8CEP");
    }

}
