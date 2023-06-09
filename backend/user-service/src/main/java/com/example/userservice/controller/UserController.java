package com.example.userservice.controller;

import com.backend.backend.util.Global;
import com.backend.backend.util.ResponseEntity;
import com.example.userservice.pojo.Users;
import com.example.userservice.service.EmailService;
import com.example.userservice.service.UserService;
import com.example.userservice.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestParam String email) {
        try {
            emailService.sendEmailVerification(email);
            return new ResponseEntity<>(Global.MAIL_SEND_SUCCESS, "验证码发送成功");
        } catch (Exception e) {
            return new ResponseEntity<>(Global.USER_LOGIN_FAIL, "服务器繁忙");
        }
    }


    @PostMapping("/confirmCode")
    public ResponseEntity<?> verifyCode(@RequestParam String email, @RequestParam String userCode) {
        // 从 Redis 中获取验证码
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String correctCode = ops.get(email);

        // 检查验证码是否存在，并验证用户输入的验证码是否正确
        if (!userCode.equals(correctCode)) {
            return new ResponseEntity<>(Global.VERIFICATION_FAIL, "验证码错误", null);
        }
        return new ResponseEntity<>(Global.VERIFICATION_SUCCESS, "验证码正确", null);
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
            String token = JwtTokenUtil.generateToken(name);
            return new ResponseEntity<>(Global.USER_REGISTER_SUCCESS, "注册成功", token);
        } else {
            return new ResponseEntity<>(Global.USER_REGISTER_FAIL, "注册失败", null);
        }
    }

    @PostMapping("/registerWithoutCode")
    public ResponseEntity<?> register(@RequestParam String username,
                                      @RequestParam String studentID,
                                      @RequestParam String email,
                                      @RequestParam String password) {
        int result = userService.registerWithoutCode(username, studentID, email, password);

        if (result == Global.USER_LOGIN_EXIST_ERROR) {
            return new ResponseEntity<>(Global.USER_LOGIN_EXIST_ERROR, "用户已存在", null);
        } else if (result == Global.USER_REGISTER_SUCCESS) {
            String token = JwtTokenUtil.generateToken(username);
            return new ResponseEntity<>(Global.USER_REGISTER_SUCCESS, "注册成功", token);
        } else {
            return new ResponseEntity<>(Global.USER_REGISTER_FAIL, "注册失败", null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestParam String studentId, @RequestParam String password) {
        try {
            Users user = userService.login(studentId, password);
            String token = JwtTokenUtil.generateToken(user.getName());
            return new ResponseEntity<>(Global.USER_LOGIN_SUCCESS, token, user);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Global.USER_LOGIN_FAIL, "学号或密码输入错误", null);
        }
    }

    @PostMapping("/loginByUsername")
    public ResponseEntity<Users> loginByUsername(@RequestParam String username, @RequestParam String password) {
        try {
            Users user = userService.loginByUsername(username, password);
            String token = JwtTokenUtil.generateToken(user.getName());
            return new ResponseEntity<>(Global.USER_LOGIN_SUCCESS, token, user);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Global.USER_LOGIN_FAIL, "用户名或密码输入错误", null);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(@RequestParam Integer userID) {
        try {
            boolean success = userService.deleteUserById(userID);
            if (success) {
                return new ResponseEntity<>(Global.USER_DELETE_SUCCESS, "用户删除成功");
            } else {
                return new ResponseEntity<>(Global.USER_NOT_FOUND, "找不到指定的用户");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Global.USER_DELETE_ERROR, "删除用户失败");
        }
    }

    @DeleteMapping("/deleteByStudentID")
    public ResponseEntity<?> deleteUserByStudentId(@RequestParam String studentID) {
        try {
            boolean success = userService.deleteUserByStudentId(studentID);
            if (success) {
                return new ResponseEntity<>(Global.USER_DELETE_SUCCESS, "用户删除成功");
            } else {
                return new ResponseEntity<>(Global.USER_NOT_FOUND, "找不到指定的用户");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Global.USER_DELETE_ERROR, "删除用户失败");
        }
    }

    @PutMapping
    public ResponseEntity<Users> updateUser(@RequestParam Integer userID, @RequestBody Users user) {
        try {
            boolean success = userService.updateUser(userID, user);
            if (success) {
                Users userById = userService.getUserById(userID);
                return new ResponseEntity<>(Global.USER_INFO_UPDATE_SUCCESS, "用户更新成功", userById);
            } else {
                return new ResponseEntity<>(Global.USER_NOT_FOUND, "找不到指定的用户");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Global.USER_INFO_UPDATE_FAIL, "更新用户失败");
        }
    }

    @GetMapping
    public ResponseEntity<Users> getUserById(@RequestParam Integer userID) {
        try {
            Users user = userService.getUserById(userID);
            if (user != null) {
                return new ResponseEntity<>(Global.SUCCESS, "用户查询成功", user);
            } else {
                return new ResponseEntity<>(Global.USER_NOT_FOUND, "找不到指定的用户", null);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Global.INTERNAL_SERVER_ERROR, "查询用户失败", null);
        }
    }

    @GetMapping("/selectByStudentID")
    public ResponseEntity<Users> getUserByStudentId(@RequestParam String studentID) {
        try {
            Users user = userService.getUserByStudentId(studentID);
            if (user != null) {
                return new ResponseEntity<>(Global.SUCCESS, "用户查询成功", user);
            } else {
                return new ResponseEntity<>(Global.USER_NOT_FOUND, "找不到指定的用户", null);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Global.INTERNAL_SERVER_ERROR, "查询用户失败", null);
        }
    }

    @GetMapping("/selectByName")
    public ResponseEntity<Users> getUserByName(@RequestParam String name) {
        try {
            Users user = userService.getUserByName(name);
            if (user != null) {
                return new ResponseEntity<>(Global.SUCCESS, "用户查询成功", user);
            } else {
                return new ResponseEntity<>(Global.USER_NOT_FOUND, "找不到指定的用户", null);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Global.INTERNAL_SERVER_ERROR, "查询用户失败", null);
        }
    }
}



