package com.jit.aquaculture.controller.knowledge;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.knowledge.Pesticide;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.knowledge.PesticideService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Api(value = "pesticide",description = "百科---农药库")
@ResponseResult
@RestController
@RequestMapping("/pesticide")
public class PesticideController {

    @Autowired
    private PesticideService pesticideService;

    /**
     * 增加农药信息
     * @param pesticide
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "增加农药信息",notes = "增加农药详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    })
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Pesticide insertPesticide(@RequestBody Pesticide pesticide) throws IOException {
        return pesticideService.insertPesticide(pesticide);
    }

    /**
     *更新某一条农药信息
     * @param id
     * @return
     */
    @ApiOperation(value = "更新某一条农药信息",notes = "更新某一条农药信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "农药id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pesticide", value = "农药信息", required = true, dataType = "Pesticide")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Pesticide updatePesticide(@PathVariable Integer id, @RequestBody Pesticide pesticide){
        return pesticideService.updatePesticide(id, pesticide);
    }

    /**
     *删除农药信息
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除农药信息",notes = "删除农药信息(可单个或批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "需要删除的农药的id（多个用“-”连接，如：1-3-4）", required = true, dataType = "String")
    })
    @RequestMapping(value = "/{ids}",method = RequestMethod.DELETE)
    public Boolean deletePesticide(@PathVariable("ids") String ids){
        return pesticideService.deletePesticide(ids);
    }

    /**
     *获取某一个农药的详细信息
     * @param id
     * @return
     */
    @ApiOperation(value = "获取某一个农药的详细信息",notes = "获取某一个农药的详细信息")
    @ApiImplicitParam(name = "id", value = "农药id", required = true, dataType = "int")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Pesticide getOnePesticide(@PathVariable Integer id){
        return pesticideService.getPesticideInfo(id);
    }

    /**
     *获取所有农药list
     * @return
     */
    @ApiOperation(value = "获取所有农药list",notes = "获取所有农药list")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageVO<Pesticide> getAllPesticides(PageQO pageQO){
        return pesticideService.getAllPesticides(pageQO);
    }

    /**
     * 根据类别获取农药list
     * @param type
     * @param pageQO
     * @return
     */
    @ApiOperation(value = "根据类别获取农药list",notes = "根据类别获取农药list")
    @ApiImplicitParam(name = "type", value = "农药类别", required = true, dataType = "string")
    @RequestMapping(value = "/type",method = RequestMethod.GET)
    public PageVO<Pesticide> getPesticidesByType(@RequestParam("type")String type, PageQO pageQO){
        return pesticideService.getPesticidesByType(type, pageQO);
    }
}
