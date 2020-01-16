package com.jit.aquaculture.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jit.aquaculture.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class JwtUser implements UserDetails {
    @JsonIgnore
    private final Integer id;
    private final String username;
//    private final String realName;
//    @JsonIgnore
    private final String password;

    @JsonIgnore
    private List<Role> roles; // 从数据库查询出的Role
    @JsonIgnore
    private final Date lastPasswordResetDate;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) {
            return null;
        }
        // 将自定义的Role转换为Security的GrantedAuthority
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role:roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
