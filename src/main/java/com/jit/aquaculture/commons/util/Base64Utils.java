package com.jit.aquaculture.commons.util;

import java.util.Base64;

/**
 * @packageName: xxz.vegetables.utils
 * @className: Base64Utils
 * @Description:
 * @author: xxz
 * @date: 2019/12/20 13:36
 */

public class Base64Utils {
    /**
     * string 转成 base64字节数组
     **/
    public static byte[] baseConvertStr(String str) {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(str.getBytes());
    }

    /**
     * 字节数组 转成 int
     **/
    public static int byteArrayToInt(byte[] bytes, int mark) {
        //因为小端，所以data[3]是高位，date[0]是低位，所以要倒过来；然后转成int时date[0]左移3*8位就是实际的值
        int value = 0;
        for (int i = 0; i < mark; i++) {
            value += (bytes[i] & 0xFF) << ((mark-1-i) * 8);
        }
        return value;
    }

}
