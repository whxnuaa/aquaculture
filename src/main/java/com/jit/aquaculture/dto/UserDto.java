package com.jit.aquaculture.dto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
public class UserDto {
    private Integer id;
    private String username;
//    @JsonIgnore
    private String password;
    private String realName;
    private Integer roleId;
    private String role;
    private String image;
    private Date registerTime;
    private Date lastPasswordResetDate;
    private Date loginTime;
    private String token;
}
