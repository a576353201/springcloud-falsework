package com.fans.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

/**
 * className: JwtTokleUtils
 *
 * @author k
 * @version 1.0
 * @description jwt令牌工具类
 * @date 2020-06-01 00:28
 **/
public class JwtTokenUtils implements Serializable {
    /**
     * salt 盐值
     */
    private final static String JWT_KEY = "kapok";
    /**
     * 过期时间 两小时
     */
    private final static Long EXPIRED_TIME_IN = 2L * 60L * 60L * 1000L;
    /**
     * 用户等级
     */
    private final static Integer SCOPE_NUMBER = 8;
    /**
     * 存储会员固定id
     */
    @Getter
    private static final String UID = "uid";
    /**
     * 存储会员等级固定key
     */
    @Getter
    private static final String SCOPE = "scope";


    public static String makeToken(Long uid) {
        return getToken(uid, SCOPE_NUMBER);
    }

    public static String makeToken(Long uid, Integer scope) {
        return getToken(uid, scope);
    }

    public static Optional<Map<String, Claim>> getClaims(String token) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT verify;
        try {
            verify = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
        return Optional.of(verify.getClaims());
    }

    private static String getToken(Long uid, Integer scope) {
        //加密规则
        Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);
        DateTime now = DateTime.now();
        return JWT.create()
                .withClaim(UID, uid)
                .withClaim(SCOPE, scope)
                .withIssuedAt(now.toDate())
                .withExpiresAt(now.plus(EXPIRED_TIME_IN).toDate())
                .sign(algorithm);
    }

    public static Boolean verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }
}
