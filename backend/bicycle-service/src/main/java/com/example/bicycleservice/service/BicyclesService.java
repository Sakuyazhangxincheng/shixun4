package com.example.bicycleservice.service;

import com.backend.backend.util.Global;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.bicycleservice.mapper.BicyclesMapper;
import com.example.bicycleservice.pojo.Bicycles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BicyclesService {
    @Autowired
    private BicyclesMapper bicyclesMapper;

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
}
