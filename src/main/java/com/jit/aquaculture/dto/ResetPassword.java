package com.jit.aquaculture.dto;

import lombok.Data;

@Data
public class ResetPassword {
    private String oldPassword;
    private String newPassword;
}
