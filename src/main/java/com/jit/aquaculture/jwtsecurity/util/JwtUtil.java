package com.jit.aquaculture.jwtsecurity.util;

import com.jit.aquaculture.dto.JwtUser;
import com.jit.aquaculture.jwtsecurity.bean.JwtProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * create by zm
 */
@Component
public class JwtUtil {


    private static final String TOKEN_HEAD="Bearer ";
    /**
     * 解析Token
     *
     * @param jsonWebToken   Token String
     * @param base64Security Base64Security Key
     * @return
     */
    public static Claims parseToken(String jsonWebToken, String base64Security) {
        try {
            jsonWebToken = jsonWebToken.replace(TOKEN_HEAD, "");
            Claims claims = Jwts.parser().setSigningKey(base64Security).parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 生成Token
     *
     * @param username 用户名
     * @param property 自定义的jwt公共属性（包括超时时长、签发者、base64Security key）
     * @return
     */
    public static String createToken(String username, JwtProperty property) {
        Calendar calendar = Calendar.getInstance();
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("created", new Date());
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT").setHeaderParam("alg", "HS256")
                .setClaims(claims)
                .setIssuer(property.getIssuer())
                .signWith(SignatureAlgorithm.HS256, property.getBase64Security())
                .setExpiration(new Date(calendar.getTimeInMillis() + property.getExpiry())).setNotBefore(calendar.getTime());
        return builder.compact();
    }

    /**
     * 获取token的创建时间
     * @param token
     * @param property
     * @return
     */
    public static Date getCreatedDateFromToken(String token, JwtProperty property) {
        Date created;
        try {
            final Claims claims = parseToken(token,property.getBase64Security());
            created = new Date((Long) claims.get("created"));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * 获取token 中的username
     * @param token
     * @return
     */
    public static String getUsernameFromToken(String token, JwtProperty property) {
        String username;
        try {
            final Claims claims = parseToken(token,property.getBase64Security());
            username = (String)claims.get("username");
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 生成token以后 是否修改过密码
     * @param created
     * @param lastPasswordReset
     * @return true：修改过，false：未修改过
     */
    private static Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    /**
     * 获取token的过期时间
     * @param token
     * @return
     */
    public static Date getExpirationDateFromToken(String token, JwtProperty property) {
        Date expiration;
        try {
            final Claims claims = parseToken(token,property.getBase64Security());
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /**
     * 判断token是否过期
     * @param token
     * @return true：已过期 ，false：未过期
     */
    private static Boolean isTokenExpired(String token, JwtProperty property) {
        final Date expiration = getExpirationDateFromToken(token, property);
        return expiration.before(new Date());
    }

    /**
     * 验证token 的有效性
     * @param token
     * @param userDetails
     * @return true:有效； false： 无效
     */
    public static Boolean validateToken(String token, UserDetails userDetails, JwtProperty property) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token, property);
        final Date created = getCreatedDateFromToken(token, property);
        //final Date expiration = getExpirationDateFromToken(token);
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token, property)
                        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
    }
}
