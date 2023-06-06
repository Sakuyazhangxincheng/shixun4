package com.example.userservice;

import com.example.userservice.service.UserService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() throws MessagingException {
        userService.deleteUserByStudentId("20231110");
    }

}
