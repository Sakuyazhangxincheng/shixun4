package com.example.bicycleservice.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bicycles")
public class Bicycles {
    @TableId(type = IdType.AUTO)
    private Integer BicycleID;
    private Integer BluetoothID;
    private String Brand;
    private String Model;
    private String BicycleCondition;
    private String Status;
    private Double Longitude;
    private Double Latitude;
}





