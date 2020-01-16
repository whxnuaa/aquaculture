package com.jit.aquaculture.controller.UsrCfg;

import com.jit.aquaculture.domain.iot.PondDO;
import com.jit.aquaculture.serviceimpl.iot.custom.PondServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @packageName: com.jit.iot.controller
 * @className: PondService
 * @author: xxz
 * @date: 2019/7/25 10:05
 */

@RestController
@CrossOrigin
@RequestMapping(value = "/pondef")
@Api(description = "塘口配置接口")
public class PondController {

    @Resource
    PondServiceImpl pondService;

    //用户新增塘口
    @ApiOperation(value = "新增塘口" ,  notes="用户新增塘口")
    @RequestMapping(value = "/addPond", method = RequestMethod.POST)
    public Boolean addPond(@RequestParam(value = "user_id") int user_id,
                           @RequestParam(value = "length") float length,
                           @RequestParam(value = "width") float width,
                           @RequestParam(value = "longitude") double longitude,
                           @RequestParam(value = "latitude") double latitude,
                           @RequestParam(value = "type") String type,
                           @RequestParam(value = "pond_name") String pond_name
                           ) throws Exception{
        //调用service
        return pondService.add_pond(user_id, length, width, longitude, latitude, type, pond_name);
    }

    //用户查询塘口信息
    @ApiOperation(value = "查询塘口" ,  notes="列出该用户下所有塘口")
    @RequestMapping(value = "/listPond", method = RequestMethod.POST)
    public List<PondDO> listPond(@RequestParam(value = "user_id") int user_id) {
        return pondService.pond_list(user_id);
    }

}
