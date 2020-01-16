package com.jit.aquaculture.serviceinterface.user;


import com.jit.aquaculture.dto.RegisterDto;
import com.jit.aquaculture.dto.UserDto;

public interface AuthService {
    RegisterDto register(RegisterDto userToAdd);
    UserDto login(String username, String password);
    String refresh(String oldToken);
}
