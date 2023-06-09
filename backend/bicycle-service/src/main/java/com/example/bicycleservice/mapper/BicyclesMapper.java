package com.example.bicycleservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bicycleservice.pojo.Bicycles;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BicyclesMapper extends BaseMapper<Bicycles> {
}
