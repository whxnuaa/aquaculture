package com.jit.aquaculture.controller.knowledge;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.knowledge.Fertilizer;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.knowledge.FertilizerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Api(value = "fertilizer",description = "百科---肥料库")
@ResponseResult
@RestController
@RequestMapping("/fertilizer")
public class FertilizerController {

    @Autowired
    private FertilizerService fertilizerService;

    /**
     * 增加农药肥料信息
     * @param fertilizer
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "增加肥料信息",notes = "增加肥料详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    })
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Fertilizer insertFertilizer(@RequestBody Fertilizer fertilizer) throws IOException {
        return fertilizerService.insertFertilizer(fertilizer);
    }

    /**
     *更新某一条肥料信息
     * @param id
     * @return
     */
    @ApiOperation(value = "更新某一条肥料信息",notes = "更新某一条肥料信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "肥料id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "fertilizer", value = "肥料信息", required = true, dataType = "Fertilizer")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Fertilizer updateFertilizer(@PathVariable Integer id, @RequestBody Fertilizer fertilizer){
        return fertilizerService.updateFertilizer(id, fertilizer);
    }

    /**
     *删除肥料信息
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除肥料信息",notes = "删除肥料信息(可单个或批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "需要删除的肥料的id（多个用“-”连接，如：1-3-4）", required = true, dataType = "String")
    })
    @RequestMapping(value = "/{ids}",method = RequestMethod.DELETE)
    public Boolean deleteFertilizer(@PathVariable("ids") String ids){
        return fertilizerService.deleteFertilizer(ids);
    }

    /**
     *获取某一个肥料的详细信息
     * @param id
     * @return
     */
    @ApiOperation(value = "获取某一个肥料的详细信息",notes = "获取某一个肥料的详细信息")
    @ApiImplicitParam(name = "id", value = "肥料id", required = true, dataType = "int")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Fertilizer getOneFertilizer(@PathVariable Integer id){
        return fertilizerService.getFertilizerInfo(id);
    }

    /**
     *获取所有肥料list
     * @return
     */
    @ApiOperation(value = "获取所有肥料list",notes = "获取所有肥料list")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageVO<Fertilizer> getAllFertilizers(PageQO pageQO){
        return fertilizerService.getAllFertilizers(pageQO);
    }

    /**
     * 根据类别获取肥料list
     * @param type
     * @param pageQO
     * @return
     */
    @ApiOperation(value = "根据类别获取肥料list",notes = "根据类别获取肥料list")
    @ApiImplicitParam(name = "type", value = "肥料类别", required = true, dataType = "string")
    @RequestMapping(value = "/type",method = RequestMethod.GET)
    public PageVO<Fertilizer> getFertilizersByType(@RequestParam("type")String type, PageQO pageQO){
        return fertilizerService.getFertilizersByType(type, pageQO);
    }
}
