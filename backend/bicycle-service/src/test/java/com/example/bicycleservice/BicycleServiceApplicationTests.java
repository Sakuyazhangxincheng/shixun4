package com.example.bicycleservice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bicycleservice.mapper.BicyclesMapper;
import com.example.bicycleservice.pojo.Bicycles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BicycleServiceApplicationTests {
    @Autowired
    private BicyclesMapper bicyclesMapper;

    @Test
    void contextLoads() {
        QueryWrapper<Bicycles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Status", "可用");
        List<Bicycles> bicycles = bicyclesMapper.selectList(queryWrapper);
        System.out.println("test");
    }
}
