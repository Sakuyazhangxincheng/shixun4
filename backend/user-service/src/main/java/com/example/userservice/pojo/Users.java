package com.example.userservice.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("users")
public class Users {
    @TableId(type = IdType.AUTO)
    private Integer UserID;
    private String Name;
    private String StudentID;
    private String PhoneNumber;
    private String Password;
    private String Avatar;
    private String Email;
}




