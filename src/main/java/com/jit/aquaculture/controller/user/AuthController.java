package com.jit.aquaculture.controller.user;



import com.jit.aquaculture.dto.RegisterDto;
import com.jit.aquaculture.dto.UserDto;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.user.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "注册登录",description = "用户注册登录账户")
@RestController
@RequestMapping("auth")
@ResponseResult
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 注册账号
     * @param addedUser
     * @return
     */
    @PostMapping(value = "signup")
    @ApiOperation(value = "注册账号",notes = "用户名、手机号和密码注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addedUser", value = "登录帐户和密码", required = true, dataType = "RegisterDto")
    })
    public RegisterDto register(@RequestBody RegisterDto addedUser) {
        return authService.register(addedUser);
    }

    /**
     * 登录系统
     * @param
     * @return
     */
    @ApiOperation(value = "登录系统",notes = "用户名或手机号和密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "登录帐户和密码", required = true, dataType = "RegisterDto")
    })
    @PostMapping(value = "login")
    public UserDto createAuthenticationToken(@RequestBody RegisterDto loginUser){
        log.info("---------------------login 1 -------------");
        return authService.login(loginUser.getUsername(),loginUser.getPassword());
    }

    /**
     * 退出
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "退出系统",notes = "退出系统，清除token")
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public Boolean logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request,response,auth);
        }
        System.out.println("auth1111: " + auth);
        return true;
    }

}
