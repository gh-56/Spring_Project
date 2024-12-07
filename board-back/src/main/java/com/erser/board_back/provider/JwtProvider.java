package com.erser.board_back.provider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider{
    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public String create(String email){
        Date expiration = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        String jwt = Jwts.builder()
                        .subject(email)
                        .issuedAt(new Date())
                        .expiration(expiration)
                        .signWith(secretKey)
                        .compact();

        return jwt;
    }

    public String validate(String jwt){
        try {
            Claims claims = Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();
            return claims.getSubject();
        } catch (UnsupportedJwtException e){
            // 지원되지 않는 JWT 형식
            System.err.println("Unsupported JWT format: " + e.getMessage());
        } catch (JwtException e){
            // JWT 파싱 또는 검증 실패
            System.err.println("Invalid JWT: " + e.getMessage());
        } catch (IllegalArgumentException e){
            // 입력된 JWT가 null, 공백 등 잘못된 값
            System.err.println("Invalid input for JWT: " + e.getMessage());
        } catch (Exception e) {
            // 그 외 예기치 않은 예외
            System.err.println("Unexpected error: " + e.getMessage());
        }
        // JWT가 유효하지 않은 경우 null 반환
        return null;
    }
}