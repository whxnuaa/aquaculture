package com.jit.aquaculture.domain.iot;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @className: PondDO
 * @author: kay
 * @date: 2019/7/26 9:18
 * @packageName: com.jit.iot.domain
 */
@Data
@AllArgsConstructor
@TableName("pondinf")
public class PondDO {
    @TableId(type = IdType.AUTO)
    private int id;
    private float  length;
    private float width;
    private double longitude;
    private double latitude;
    private String type;
    private String name;
    private int userid;

    public PondDO(int pdid){
        id = pdid;
    }

    public PondDO(float len, float wd, double lon, double lat, String tp,String nm, int usrid){
        length = len;
        width = wd;
        longitude = lon;
        latitude = lat;
        type = tp;
        name = nm;
        userid = usrid;
    }
}
