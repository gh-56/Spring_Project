package com.erser.board_back.filter;

import java.io.IOException;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.erser.board_back.provider.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * * JWT 인증을 처리하는 Spring Security 필터.
 * * HTTP 요청의 Authorization 헤더에서 JWT를 추출하고 검증하여 사용자 인증 정보를 SecurityContext에 저장합니다.
 * 
 * <p>필터 동작:
 * <ul>
 *     <li>Authorization 헤더에서 Bearer 토큰 추출</li>
 *     <li>JWT 검증 및 사용자 식별</li>
 *     <li>SecurityContext에 인증 정보 설정</li>
 * </ul>
 * 
 * <p>이 필터는 모든 요청에서 실행되며, 유효하지 않은 JWT는 필터 체인을 통해 다음 단계로 전달됩니다.
 *
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final JwtProvider jwtProvider;

     /**
     * * HTTP 요청에서 JWT를 추출하고 검증한 뒤, SecurityContext에 인증 정보를 설정합니다.
     * 
     * @param request  클라이언트로부터 받은 HTTP 요청
     * @param response 서버에서 클라이언트로 보낼 HTTP 응답
     * @param filterChain 필터 체인을 통해 요청을 다음 필터로 전달
     * @throws ServletException 필터 처리 중 서블릿 오류가 발생한 경우
     * @throws IOException 입력/출력 오류가 발생한 경우
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            try{
                String token = parseBearerToken(request);
                if (token == null){
                    filterChain.doFilter(request, response);
                    return;
                }

                String email = jwtProvider.validate(token);
                if (email == null){
                    filterChain.doFilter(request, response);
                    return;
                }

                AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.NO_AUTHORITIES);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authenticationToken);

                SecurityContextHolder.setContext(securityContext);
            } catch (Exception e){
                e.printStackTrace();
            }
            filterChain.doFilter(request, response);
    }

    /**
     * HTTP 요청의 Authorization 헤더에서 Bearer 토큰을 추출합니다.
     *
     * @param request 클라이언트로부터 받은 HTTP 요청
     * @return Bearer 토큰 문자열, 없을 경우 null
     */
    private String parseBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        boolean hasAuthorization = StringUtils.hasText(authorization);
        if (!hasAuthorization) return null;

        boolean isBearer = authorization.startsWith("Bearer ");
        if (!isBearer) return null;

        String token = authorization.substring(7);
        return token;
    }

}
