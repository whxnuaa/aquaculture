package com.jit.aquaculture.domain.iot;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: Sensor
 * @author: kay
 * @date: 2019/7/26 9:18
 * @packageName: com.jit.iot.domain
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("termdef")
public class TermDO {
    @TableId(type = IdType.AUTO)
    private int id;
    private int type;       // 1:tcp485, 2:单参数lora终端, 3:lora+485, 4:北京农芯终端
    private String deveui ; //seeed和农芯的设备串号
    private String name;    //设备自定义名称
    private int usrid;
    private int status;     // 1:on, 0:off

    public TermDO(int term_type, int userid, String term_name){
        type = term_type;
        deveui = null;
        usrid = userid;
        name = term_name;
        status = 0;
    }

    public TermDO(int term_type, String dev_eui, int userid, String term_name){
        type = term_type;
        deveui = dev_eui;
        usrid = userid;
        name = term_name;
        status = 0;
    }
}
