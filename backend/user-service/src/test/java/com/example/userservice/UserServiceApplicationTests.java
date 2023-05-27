package com.example.userservice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.userservice.mapper.UsersMapper;
import com.example.userservice.pojo.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserServiceApplicationTests {

    @Autowired
    private UsersMapper usersMapper;

    @Test
    void contextLoads() {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        List<Users> users = usersMapper.selectList(queryWrapper);
        for (Users user : users) {
            System.out.println(user);
        }
    }

}
