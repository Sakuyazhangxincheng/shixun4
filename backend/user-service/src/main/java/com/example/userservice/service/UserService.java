package com.example.userservice.service;

import com.backend.backend.util.Global;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.userservice.mapper.UsersMapper;
import com.example.userservice.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public int register(String name, String studentId, String email, String password, String phoneNumber, String verificationCode) {
        // 从 Redis 中获取验证码
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String correctCode = ops.get(email);

        // 检查验证码是否存在，并验证用户输入的验证码是否正确
        if (!verificationCode.equals(correctCode)) {
            return Global.VERIFICATION_FAIL;
        }

        // 检查邮箱和学生ID是否已经存在
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Email", email).or().eq("StudentID", studentId);
        Users existingUser = usersMapper.selectOne(queryWrapper);
        if (existingUser != null) {
            return Global.USER_LOGIN_EXIST_ERROR;
        }

        // 验证成功，创建新用户并保存到数据库
        Users user = new Users(null, name, studentId, phoneNumber, password, null, email);
        usersMapper.insert(user);

        // 清除 Redis 中的验证码
        redisTemplate.delete(email);

        return Global.USER_REGISTER_SUCCESS;
    }

    public Users login(String studentId, String password) {
        // 检查学号和密码是否为空
        if (StringUtils.isEmpty(studentId) || StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("学号和密码都不能为空");
        }

        // 查询数据库
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("StudentID", studentId);
        Users existingUser = usersMapper.selectOne(queryWrapper);

        // 检查学号是否存在
        if (existingUser == null) {
            throw new IllegalArgumentException("学号不存在");
        }

        // 验证密码是否正确
        if (!existingUser.getPassword().equals(password)) {
            throw new IllegalArgumentException("密码错误");
        }

        return existingUser;
    }
}



