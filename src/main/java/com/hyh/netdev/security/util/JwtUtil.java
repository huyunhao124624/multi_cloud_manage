package com.hyh.netdev.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

/**
 * JWT工具
 *
 * @author Albumen
 */
@Component
public class JwtUtil {
    @Value("${security.jwtDefaultExp}")
    Integer expTime;

    private static Algorithm algorithm;

    public JwtUtil() {
        RSAPublicKey publicKey = RsaKeyUtil.getInstance().getPublicKey();
        RSAPrivateKey privateKey = RsaKeyUtil.getInstance().getPrivateKey();
        algorithm = Algorithm.RSA256(publicKey, privateKey);
    }

    public String create(String userName) {
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer("Albumen")
                    .withSubject(userName)
                    .withExpiresAt(new Date(System.currentTimeMillis() + Long.valueOf(expTime) * 1000L))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            return null;
        }
        return token;
    }

    public String create(String userName, Integer userId) {
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer("Albumen")
                    .withSubject(userName)
                    .withExpiresAt(new Date(System.currentTimeMillis() + Long.valueOf(expTime) * 1000L))
                    .withClaim("UserId", userId)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            return null;
        }
        return token;
    }

    public String getUuid(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Albumen")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public Integer getUserId(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Albumen")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("UserId").asInt();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }
}
