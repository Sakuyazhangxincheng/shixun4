package com.example.bicycleservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bicycleservice.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
