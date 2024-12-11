package com.erser.board_back.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.erser.board_back.filter.JwtAuthenticationFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * * Spring Security의 설정을 정의하는 클래스.
 * 
 * <p>JWT 인증 기반의 보안을 적용하며, CORS, CSRF, 세션 관리, 요청 허가, 예외 처리를 포함한 
 * 다양한 보안 설정을 제공합니다.</p>
 *
 * <p>특징:</p>
 * <ul>
 *     <li>Stateless 인증 방식 사용: 세션을 생성하지 않고 JWT를 활용하여 인증 처리.</li>
 *     <li>CORS 허용 설정: 모든 출처와 HTTP 메서드를 허용.</li>
 *     <li>CSRF 비활성화: REST API에 적합하도록 CSRF 보호 비활성화.</li>
 *     <li>JWT 필터 추가: 요청이 컨트롤러에 도달하기 전에 JWT 인증 필터를 실행.</li>
 * </ul>
 *
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

     /**
     * Spring Security의 필터 체인을 정의합니다.
     * 
     * <p>JWT 인증, CORS, CSRF 비활성화, 세션 정책, 요청 허가 및 인증 실패 처리 등의 보안 정책을 설정합니다.</p>
     * 
     * @param httpSecurity Spring Security의 HTTP 보안 설정 객체
     * @return Spring Security의 필터 체인
     * @throws Exception 보안 설정 중 발생할 수 있는 예외
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            // * CORS 설정
            .cors(cors -> cors
                .configurationSource(corsConfigrationSource())
            )
            // * CSRF 비활성화
            .csrf(CsrfConfigurer::disable)
            // * HTTP basic 인증 비활성화
            .httpBasic(HttpBasicConfigurer::disable)
            // * 세션 관리 정책: STATELESS
            .sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // * 요청 인증 및 권한 설정
            .authorizeHttpRequests(request -> request
                .requestMatchers("/", "/api/v1/auth/**", "/api/v1/search/**", "/file/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/board/**", "/api/v1/user/*").permitAll()
                .anyRequest().authenticated()
            )
            // * 인증 실패 시 응답 처리
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(new FailedAuthenticationEntryPoint())
            )
            // * JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    /**
     * * CORS 설정을 정의하는 메서드.
     * 
     * <p>모든 출처, HTTP 메서드, 헤더를 허용하도록 설정합니다.</p>
     * 
     * @return CORS 설정을 적용한 {@link CorsConfigurationSource}
     */
    @Bean
    public CorsConfigurationSource corsConfigrationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}

/**
 * * 인증 실패 시 HTTP 응답을 처리하는 클래스.
 * 
 * <p>Spring Security에서 인증되지 않은 요청이 발생하면, 클라이언트에 403 상태 코드와 JSON 형식의 
 * 에러 메시지를 반환합니다.</p>
 *
 * <p>응답 내용 예:</p>
 * <pre>
 * {
 *   "code": "NP",
 *   "message": "Do not have permission."
 * }
 * </pre>
 */
class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint{
    /**
     * * 인증되지 않은 요청이 들어왔을 때 호출됩니다.
     * 
     * <p>HTTP 응답에 403 상태 코드와 JSON 에러 메시지를 포함하여 반환합니다.</p>
     * 
     * @param request 클라이언트의 HTTP 요청
     * @param response 서버가 클라이언트로 보낼 HTTP 응답
     * @param authException 인증 실패와 관련된 예외
     * @throws IOException 입력/출력 오류가 발생한 경우
     * @throws ServletException 서블릿 오류가 발생한 경우
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"code\": \"AF\", \"message\" : \"Authorization Failed.\"}");
    }
}
