package com.example.bicycleservice.controller;

import com.backend.backend.util.Global;
import com.backend.backend.util.ResponseEntity;
import com.example.bicycleservice.pojo.Bicycles;
import com.example.bicycleservice.pojo.Orders;
import com.example.bicycleservice.service.BicyclesService;
import com.example.bicycleservice.util.MultipleLinearRegression;
import com.example.bicycleservice.util.Statistics;
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

    // 前端传入报修信息，后端首先判断传入的蓝牙ID是否正确，然后更新bicycles表中的车辆状况，并将订单状况改为“维修中”，最后插入新的repairs表信息
    @PostMapping("/addRepairOrder")
    public ResponseEntity<?> addRepairOrder(@RequestParam String bluetoothID, @RequestParam String bicycleCondition) {
        try {
            int result = bicyclesService.addRepairOrder(bluetoothID, bicycleCondition);
            if (result == Global.VEHICLE_SEARCH_ERROR) {
                return new ResponseEntity<>(Global.VEHICLE_SEARCH_ERROR, "没有找到对应蓝牙ID的车辆");
            } else if (result == Global.VEHICLE_UPDATE_FAIL) {
                return new ResponseEntity<>(Global.VEHICLE_UPDATE_FAIL, "更新车辆状态信息失败");
            } else if (result == Global.REPORT_ISSUE_FAIL) {
                return new ResponseEntity<>(Global.REPORT_ISSUE_FAIL, "创建新的维修信息失败");
            } else if (result == Global.REPORT_ISSUE_SUCCESS) {
                return new ResponseEntity<>(Global.REPORT_ISSUE_SUCCESS, "维修信息登记成功");
            } else {
                return new ResponseEntity<>(Global.INTERNAL_SERVER_ERROR, "服务端出错");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Global.INTERNAL_SERVER_ERROR, "服务端出错");
        }
    }

    // 前端传入6个浮点数数据，后端返回预测结果
    @GetMapping("/predict")
    public ResponseEntity<?> predict(@RequestParam double p1,
                                     @RequestParam double p2,
                                     @RequestParam double p3,
                                     @RequestParam double p4,
                                     @RequestParam double p5,
                                     @RequestParam String p6) {
        double[][] x = Statistics.getX();
        double[] y = Statistics.getY();
        MultipleLinearRegression mlr = new MultipleLinearRegression(x, y);
        double p7 = 0;
        if(p6.equals("y")) p7 = 1;
        else if (p6.equals("s")) p7 = 2;
        else if (p6.equals("x")) p7 = 3;
        else if (p6.equals("n")) p7 = 4;
        double[] input = {p1, p2, p3, p4, p5, p7};
        double prediction = mlr.predict(input);
        return new ResponseEntity<>(Global.SUCCESS, "预测结果", prediction);
    }

    // 返回所有订单数据
    @GetMapping("/getAllOrders")
    public ResponseEntity<?> getAllOrders() {
        List<Orders> orders = bicyclesService.getAllOrders();
        return new ResponseEntity<>(Global.SUCCESS, "查询成功", orders);
    }

    // 根据订单ID删除对应的订单
    @DeleteMapping("/deleteOrderByID")
    public ResponseEntity<?> deleteOrderByID(@RequestParam int orderID) {
        int result = bicyclesService.deleteOrderByID(orderID);
        return new ResponseEntity<>(result, "删除完成");
    }

    // 根据订单ID和新的订单信息修改旧的订单信息
    @PutMapping("/updateOrderByID")
    public ResponseEntity<?> updateOrderByID(@RequestParam int orderID, @RequestBody Orders newOrder) {
        int result = bicyclesService.updateOrderByID(orderID, newOrder);
        return new ResponseEntity<>(result, "更新结束");
    }
}
