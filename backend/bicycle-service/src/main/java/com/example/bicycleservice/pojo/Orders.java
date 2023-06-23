package com.example.bicycleservice.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("orders")
public class Orders {
    @TableId(type = IdType.AUTO)
    private Integer OrderID;
    private Integer UserID;
    private Integer BicycleID;
    private LocalDateTime StartTime;
    private LocalDateTime EndTime;
    private Double Mileage;
    private Double Cost;
    private Double StartLongitude;
    private Double StartLatitude;
    private Double EndLongitude;
    private Double EndLatitude;
}
