package com.jit.aquaculture.jwtsecurity.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 自定义加密方式
 * 采用MD5加密方式
 */
@Slf4j
public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        StringBuffer buf = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(charSequence.toString().getBytes());
            byte b[] = md.digest();
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            String str = buf.toString();
            return (str.substring(10, str.length()) + str.substring(0, 10));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("加密 error {}",e);
        }
        return buf.toString();
    }

    /**
     * 比较
     * @param charSequence 未加密的密码
     * @param s 已加密的密码
     * @return
     */
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return encode(charSequence).equals(s);
    }
}
