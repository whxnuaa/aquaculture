package com.jit.aquaculture.serviceinterface.user;



import com.github.pagehelper.PageInfo;
import com.jit.aquaculture.domain.user.User;
import com.jit.aquaculture.dto.ResetPassword;
import com.jit.aquaculture.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    PageInfo<UserDto> getList(Integer pageNum, Integer pageSize);

    UserDto getOneUserByAdmin(Integer userId);
    UserDto getOneUserByUser();

    UserDto resetPassword(HttpServletRequest request, ResetPassword resetPassword);

    User addUser(User userDO);

    User resetInfo(User userDO);

    Boolean deleteUserInfo(Integer userId);


}
