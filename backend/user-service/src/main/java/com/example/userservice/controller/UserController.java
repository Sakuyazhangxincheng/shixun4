package com.example.userservice.controller;

import com.backend.backend.util.Global;
import com.backend.backend.util.ResponseEntity;
import com.example.userservice.pojo.Users;
import com.example.userservice.service.EmailService;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestParam String email) {
        try {
            emailService.sendEmailVerification(email);
            return new ResponseEntity<>(Global.MAIL_SEND_SUCCESS, "验证码发送成功");
        } catch (Exception e) {
            return new ResponseEntity<>(Global.USER_LOGIN_FAIL, "服务器繁忙");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String name,
                            @RequestParam String studentId,
                            @RequestParam String email,
                            @RequestParam String password,
                            @RequestParam String phoneNumber,
                            @RequestParam String verificationCode) {
        int result = userService.register(name, studentId, email, password, phoneNumber, verificationCode);

        if (result == Global.VERIFICATION_FAIL) {
            return new ResponseEntity<>(Global.VERIFICATION_FAIL, "验证码错误", null);
        } else if (result == Global.USER_LOGIN_EXIST_ERROR) {
            return new ResponseEntity<>(Global.USER_LOGIN_EXIST_ERROR, "用户已存在", null);
        } else if (result == Global.USER_REGISTER_SUCCESS) {
            return new ResponseEntity<>(Global.USER_REGISTER_SUCCESS, "注册成功", null);
        } else {
            return new ResponseEntity<>(Global.USER_REGISTER_FAIL, "注册失败", null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestParam String studentId, @RequestParam String password) {
        try {
            Users user = userService.login(studentId, password);
            return new ResponseEntity<>(Global.USER_LOGIN_SUCCESS, "登录成功", user);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Global.USER_LOGIN_FAIL, "学号或密码输入错误", null);
        }
    }
}



