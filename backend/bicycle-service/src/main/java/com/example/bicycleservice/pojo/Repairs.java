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
@TableName("repairs")
public class Repairs {
    @TableId(type = IdType.AUTO)
    private Integer RepairID;
    private Integer BicycleID;
    private String FaultType;
    private String FaultDescription;
    private Integer Priority;
    private String RepairStatus;
    private String Notes;
}
