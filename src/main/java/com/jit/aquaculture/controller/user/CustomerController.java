package com.jit.aquaculture.controller.user;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.user.Customer;
import com.jit.aquaculture.dto.CustomerDto;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.user.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "普通用户信息",description = "用户——普通用户")
@ResponseResult
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @ApiOperation(value = "管理员增加一般用户信息",notes = "管理员增加一般用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "customer", value = "一般用户信息", required = true, dataType = "Customer")
    })
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Customer addCustomer(@RequestBody Customer customer){
        return customerService.insertCustomer(customer);
    }

    @ApiOperation(value = "管理员和当前用户可更新一般用户信息",notes = "更新一般用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "customer", value = "一般用户信息", required = true, dataType = "Customer")
    })
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Customer updateCustomer(@RequestBody Customer customer){
        return customerService.updateCustomer(customer);
    }

    @ApiOperation(value = "管理员可以删除一般用户信息",notes = "管理员删除一般用户信息，用“-”隔开")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "一般用户id", required = true, dataType = "string")
    })
    @RequestMapping(value = "/{ids}",method = RequestMethod.DELETE)
    public Boolean deleteCustomer(@PathVariable String ids){
        return customerService.deleteCustomer(ids);
    }


    @ApiOperation(value = "获取一个一般用户信息",notes = "所有角色都可获取一个一般用户的详细信息")
    @ApiImplicitParam(name = "username", value = "一般用户username", required = true, dataType = "string")
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public CustomerDto getOne(@RequestParam("username")String username){
        return customerService.getOne(username);
    }

    @ApiOperation(value = "获取所有一般用户信息",notes = "所有角色都可获取所有一般用户信息")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageVO<CustomerDto> getAll(PageQO pageQO){
        return customerService.getAll(pageQO);
    }
}
