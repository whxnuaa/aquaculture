package com.jit.aquaculture.domain.user;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String real_name;
    private String image;
    private Date register_time;
    private Boolean videoState;
//    private Date lastPasswordResetDate;
//    private Date loginTime;
//    private String tel;
//    private String email;
//    private String number;
//    private String department;
//    private String remark;
    private String role;

}