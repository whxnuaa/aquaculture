package com.jit.aquaculture.controller.user;


import com.github.pagehelper.PageInfo;
import com.jit.aquaculture.domain.user.User;
import com.jit.aquaculture.dto.ResetPassword;
import com.jit.aquaculture.dto.UserDto;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@Api(value = "用户管理",description = "用户基本信息管理")
@RestController
@RequestMapping("user")
@ResponseResult
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 管理员分页获取全部用户
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     * @return 用户列表
     */
    @ApiOperation(value = "管理员分页获取全部用户",notes = "管理员分页获取全部用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "起始页码", required = false, dataType = "int"),
            @ApiImplicitParam(name="pageSize",value = "每页数量",required = false,dataType = "int")
    })
    @GetMapping("admin")
    PageInfo<UserDto> getLists(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        return userService.getList(pageNum,pageSize);
    }

    /**
     * 管理员获取某一用户的信息
     * @param userId
     * @return
     */
    @ApiOperation(value = "管理员获取某一用户的信息",notes = "管理员获取某一用户的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int")
    })
    @GetMapping("admin/one")
    UserDto getOneUserbyAdmin(@RequestParam("userId")Integer userId){
        return userService.getOneUserByAdmin(userId);
    }

    /**
     * 用户获取个人信息
     * @return
     */
    @ApiOperation(value = "用户获取个人信息",notes = "用户获取个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    })
    @GetMapping("user/one")
    UserDto getOneUserbyUser(){
        return userService.getOneUserByUser();
    }

    /**
     * 重置密码
     * @param request HttpServlet 请求
     * @param resetPassword 密码对象
     * @return 带有token的用户信息
     */
    @ApiOperation(value = "重置密码",notes = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "resetPassword", value = "密码对对象", required = true, dataType = "ResetPassword")
    })
    @PutMapping("user/resetpassword")
    UserDto resetPassword(HttpServletRequest request, @RequestBody ResetPassword resetPassword){
        return userService.resetPassword(request,resetPassword);
    }

    /**
     * 新增用户信息
     * @param userDO
     * @return
     */
    @ApiOperation(value = "新增用户信息",notes = "管理员增加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userDO", value = "用户信息", required = true, dataType = "User")
    })
    @PostMapping("admin/add")
    User addUser(@RequestBody User userDO){
        return userService.addUser(userDO);
    }

    /**
     *更新用户信息
     * @param userDO
     * @return
     */
    @ApiOperation(value = "更新用户信息",notes = "管理员更新用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userDO", value = "用户信息", required = true, dataType = "User")
    })
    @PutMapping("user/resetinfo")
    User resetUserInfo( @RequestBody User userDO){
        return userService.resetInfo(userDO);
    }

    /**
     * 管理员删除用户
     * @param userId
     * @return
     */
    @ApiOperation(value = "管理员删除用户",notes = "管理员删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int")
    })
    @DeleteMapping("admin/{userId}")
    Boolean deleteUserInfo(@PathVariable Integer userId){
        return userService.deleteUserInfo(userId);
    }



}
