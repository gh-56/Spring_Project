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

/**
 * * JWT 토큰을 공급하기 위한 클래스.
 * * 이 클래스는 JWT를 생성하고 검증하는 기능을 제공합니다.
 */
@Component
public class JwtProvider{
    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    /**
     * * 사용자 이메일을 기반으로 JWT를 생성합니다.
     * * 토큰은 1시간 후 만료되도록 설정됩니다.
     * 
     * @param email JWT에 포함할 사용자 이메일
     * @return 생성된 JWT 토큰 문자열
     */
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

    /**
     * * 주어진 JWT를 검증하는 메서드입니다.
     * 
     * <ul>
     *      <li> JWT 서명을 secretKey로 검증 </li>
     *      <li> 토큰의 만료 여부 확인 </li>
     *      <li> 토큰의 유효성 검증 </li>
     * </ul>
     * 
     * 
     * @param jwt 검증할 대상이 될 JWT 토큰 문자열
     * @return JWT가 유효하면 토큰의 subject(사용자 이메일), 유효하지 않으면 null
     * 
     * @throws UnsupportedJwtException JWT 형식이 지원되지 않을 경우
     * @throws IllegalArgumentException 입력된 JWT가 null, 공백 등 잘못된 값
     * @throws JwtException JWT 파싱 또는 검증 실패
     * @throws Exception 그 외 예기치 않은 오류 발생 시
     */

    public String validate(String jwt){
        try {
            Claims claims = Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();
            return claims.getSubject();
        } catch (UnsupportedJwtException e){
            System.err.println("Unsupported JWT format: " + e.getMessage());
        } catch (JwtException e){
            System.err.println("Invalid JWT: " + e.getMessage());
        } catch (IllegalArgumentException e){
            System.err.println("Invalid input for JWT: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
        return null;
    }
}