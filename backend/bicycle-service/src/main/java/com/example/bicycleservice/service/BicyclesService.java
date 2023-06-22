package com.example.bicycleservice.service;

import com.backend.backend.util.Global;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.bicycleservice.mapper.BicyclesMapper;
import com.example.bicycleservice.mapper.RepairsMapper;
import com.example.bicycleservice.pojo.Bicycles;
import com.example.bicycleservice.pojo.Repairs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BicyclesService {
    @Autowired
    private BicyclesMapper bicyclesMapper;

    @Autowired
    private RepairsMapper repairsMapper;

    // 查询所有”可用“状态的车辆
    public List<Bicycles> getLatLngOfAvailable() {
        QueryWrapper<Bicycles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Status", "可用");
        return bicyclesMapper.selectList(queryWrapper);
    }

    // 根据蓝牙ID和经纬度更新车辆的信息
    public int updateBicycleByLaLng(int bluetoothID, double latitude, double longitude) {
        QueryWrapper<Bicycles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("BluetoothID", bluetoothID);
        Bicycles bicycle = bicyclesMapper.selectOne(queryWrapper);

        // 如果未查询到对象
        if (bicycle == null) {
            return Global.VEHICLE_SEARCH_ERROR;
        }

        bicycle.setLatitude(latitude);
        bicycle.setLongitude(longitude);

        UpdateWrapper<Bicycles> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("BluetoothID", bluetoothID);
        bicyclesMapper.update(bicycle, updateWrapper);
        return Global.VEHICLE_UPDATE_SUCCESS;
    }

    // 前端传入报修信息，后端首先判断传入的蓝牙ID是否正确，然后更新bicycles表中的车辆状况，并将订单状况改为“维修中”，最后插入新的repairs表信息
    public int addRepairOrder(String bluetoothID, String BicycleCondition) {
        // 1 判断传入的蓝牙ID在数据库中是否存在
        QueryWrapper<Bicycles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("BluetoothID", bluetoothID);
        Bicycles targetBicycle = bicyclesMapper.selectOne(queryWrapper);
        // 1.1 如果该蓝牙对应的车辆不存在，则返回错误信息
        if (targetBicycle == null) {
            return Global.VEHICLE_SEARCH_ERROR;
        }

        // 2 更新bicycles表相关信息
        targetBicycle.setBicycleCondition(BicycleCondition);
        targetBicycle.setStatus("维修中");
        UpdateWrapper<Bicycles> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("BluetoothID", bluetoothID);
        int updateResult = bicyclesMapper.update(targetBicycle, updateWrapper);
        // 2.1 如果更新失败则返回信息
        if (updateResult == 0) {
            return Global.VEHICLE_UPDATE_FAIL;
        }

        // 3 插入repair表新的数据
        Repairs repair = new Repairs();
        repair.setBicycleID(targetBicycle.getBicycleID());
        repair.setFaultType(BicycleCondition);
        repair.setRepairStatus("待修");
        // 3.1 如果插入失败返回信息
        if (repairsMapper.insert(repair) == 0) {
            return Global.REPORT_ISSUE_FAIL;
        }

        return Global.REPORT_ISSUE_SUCCESS;
    }
}
