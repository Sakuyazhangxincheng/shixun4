package com.example.bicycleservice.controller;

import com.backend.backend.util.Global;
import com.backend.backend.util.ResponseEntity;
import com.example.bicycleservice.pojo.Bicycles;
import com.example.bicycleservice.service.BicyclesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bicycles")
public class BicyclesController {
    @Autowired
    private BicyclesService bicyclesService;

    // 查询所有可使用状态的车辆
    @GetMapping("/getLatLng")
    public ResponseEntity<?> getLatLngOfAvailable() {
        try {
            List<Bicycles> bicycles = bicyclesService.getLatLngOfAvailable();
            return new ResponseEntity<>(bicycles.size(), "查询完成", bicycles);
        } catch (Exception e) {
            return new ResponseEntity<>(Global.INTERNAL_SERVER_ERROR, "查询失败");
        }
    }

    // 根据蓝牙ID和经纬度更新车辆信息
    @PutMapping("/sendLatLng")
    public ResponseEntity<?> sendLatLng(@RequestParam int bluetoothID,
                                        @RequestParam double latitude,
                                        @RequestParam double longitude) {
        try {
            int result = bicyclesService.updateBicycleByLaLng(bluetoothID, latitude, longitude);
            if (result == Global.VEHICLE_SEARCH_ERROR) {
                return new ResponseEntity<>(Global.VEHICLE_SEARCH_ERROR, "未查询到蓝牙对应的车辆");
            } else if (result == Global.VEHICLE_UPDATE_SUCCESS){
                return new ResponseEntity<>(Global.VEHICLE_UPDATE_SUCCESS, "更新车辆位置信息成功");
            } else {
                return new ResponseEntity<>(Global.INTERNAL_SERVER_ERROR, "更新车辆位置信息失败");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Global.INTERNAL_SERVER_ERROR, "更新车辆位置信息失败");
        }
    }
}
