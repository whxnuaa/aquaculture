package com.jit.aquaculture.jwtsecurity.bean;

import lombok.Data;

/**
 *  by zm
 *  jwt公共属性
 */
@Data
//@Component
public class JwtProperty {
    private long expiry; // 超时时长
    private String issuer; // 签发者
    private String base64Security; // base64Security key
}
