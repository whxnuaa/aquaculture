package com.jit.aquaculture.rbac;

import org.springframework.security.access.ConfigAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yin on 2017/10/10.
 */
public class MyConfigAttribute implements ConfigAttribute {
    private final HttpServletRequest httpServletRequest;

    public MyConfigAttribute(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public String getAttribute() {
        return null;
    }
    public HttpServletRequest getHttpServletRequest(){
        return httpServletRequest;
    }
}
