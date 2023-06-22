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

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 获取所有的用户信息
    public List<Users> getAllUsers() {
        return usersMapper.selectList(new QueryWrapper<>());
    }

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

    public int registerWithoutCode(String username, String studentID, String email, String password) {
        // 检查邮箱和学生ID是否已经存在
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Email", email).or().eq("StudentID", studentID);
        Users existingUser = usersMapper.selectOne(queryWrapper);
        if (existingUser != null) {
            return Global.USER_LOGIN_EXIST_ERROR;
        }

        // 验证成功，创建新用户并保存到数据库
        Users user = new Users(null, username, studentID, null, password, null, email);
        usersMapper.insert(user);

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

    public Users loginByUsername(String username, String password) {
        // 检查用户名和密码是否为空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("学号和密码都不能为空");
        }

        // 查询数据库
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Name", username);
        Users existingUser = usersMapper.selectOne(queryWrapper);

        // 检查用户是否存在
        if (existingUser == null) {
            throw new IllegalArgumentException("用户不存在");
        }

        // 验证密码是否正确
        if (!existingUser.getPassword().equals(password)) {
            throw new IllegalArgumentException("密码错误");
        }

        return existingUser;
    }

    // 通过userID删除对应的用户
    public boolean deleteUserById(Integer userId) {
        Users user = usersMapper.selectById(userId);
        if (user != null) {
            int rows = usersMapper.deleteById(userId);
            return rows > 0;
        }
        return false;
    }

    // 根据StudentID删除对应的用户
    public boolean deleteUserByStudentId(String studentId) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("StudentID", studentId);
        Users user = usersMapper.selectOne(queryWrapper);
        if (user != null) {
            int rows = usersMapper.delete(queryWrapper);
            return rows > 0;
        }
        return false;
    }

    // 传入新的User对象，更新对应的用户信息
    public boolean updateUser(int userID, Users newUser) {
        Users user = usersMapper.selectById(userID);
        if (user != null) {
            newUser.setUserID(user.getUserID());
            if (newUser.getName() == null) {
                newUser.setName(user.getName());
            }
            if (newUser.getStudentID() == null) {
                newUser.setStudentID(user.getStudentID());
            }
            if (newUser.getAvatar() == null) {
                newUser.setAvatar(user.getAvatar());
            }
            if (newUser.getEmail() == null) {
                newUser.setEmail(user.getEmail());
            }
            if (newUser.getPassword() == null) {
                newUser.setPassword(user.getPassword());
            }
            if (newUser.getPhoneNumber() == null) {
                newUser.setPhoneNumber(user.getPhoneNumber());
            }
            int rows = usersMapper.updateById(newUser);
            return rows > 0;
        }
        return false;
    }

    // 根据UserID查询对应的用户
    public Users getUserById(Integer userId) {
        return usersMapper.selectById(userId);
    }

    // 根据studentID查询对应的用户
    public Users getUserByStudentId(String studentId) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("StudentID", studentId);
        return usersMapper.selectOne(queryWrapper);
    }

    // 根据Name查询对应的用户
    public Users getUserByName(String name) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Name", name);
        return usersMapper.selectOne(queryWrapper);
    }
}



