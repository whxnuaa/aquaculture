package com.jit.aquaculture.controller.user;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.user.Expert;
import com.jit.aquaculture.dto.ExpertDto;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.user.ExpertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "专家信息",description = "用户——专家")
@ResponseResult
@RestController
@RequestMapping("/expert")
public class ExpertController {

    @Autowired
    private ExpertService expertService;

    @ApiOperation(value = "管理员增加专家信息",notes = "管理员增加专家信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "expert", value = "专家信息", required = true, dataType = "Expert")
    })
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Expert addExpert(@RequestBody Expert expert){
        return expertService.insertExpert(expert);
    }

    @ApiOperation(value = "管理员和专家可更新专家信息",notes = "更新专家信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "expert", value = "专家信息", required = true, dataType = "Expert")
    })
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Expert updateExpert(@RequestBody Expert expert){
        return expertService.updateExpert(expert);
    }

    @ApiOperation(value = "管理员可以删除专家信息",notes = "管理员删除专家信息，用“-”隔开\"")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "专家id", required = true, dataType = "string")
    })
    @RequestMapping(value = "/{ids}",method = RequestMethod.DELETE)
    public Boolean deleteExpert(@PathVariable("ids") String ids){
        return expertService.deleteExpert(ids);
    }

    @ApiOperation(value = "获取一个专家信息",notes = "所有角色都可获取一个专家的详细信息")
    @ApiImplicitParam(name = "username", value = "专家username", required = true, dataType = "string")
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public ExpertDto getOne(@RequestParam("username") String username){
        return expertService.getOne(username);
    }

    @ApiOperation(value = "获取所有专家信息",notes = "所有角色都可获取所有专家信息")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageVO<ExpertDto> getAll(PageQO pageQO){
        return expertService.getAll(pageQO);
    }
}
