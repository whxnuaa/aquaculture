package com.jit.aquaculture.domain.user;

import java.util.List;

public class Permission {
    private Integer id;
    private String name;
    private String description;
    private String url;
    private String method;
    private List<Role> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Permission() {
    }

    public Permission(Integer id, String name, String description, String url, String method, List<Role> roles) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.method = method;
        this.roles = roles;
    }
}
