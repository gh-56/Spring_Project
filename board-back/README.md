# 새로 알게 된 것들
## Spring security : JWT (Json Web Token)
- io.jsonwebtoken:jjwt:0.12.6 (24년 12월 7일 기준 가장 최신 버전) [github 페이지에서 확인하기](https://github.com/jwtk/jjwt)

### JWT 사용 및 구조
```java
@Component
public class JwtProvider {
    private final SecertKey secretKey = Jwts.SIG.HS256.key().build();

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
}
```
- jwt는 header, payload, signature로 구성됨
1. Header
    - 알고리즘 정보(HS256)와 타입(jwt)이 포함됨
    - Jwts.builder()를 사용하면 자동생성됨
2. Payload
    - 사용자에 대한 데이터를 담는 부분으로, **클레임(Claims)** 이라고 부름
        - sub : subject -> 사용자 식별
        - iat : issued At -> JWT 발급 시간
        - exp : expiration time -> JWT 만료 시간
3. Signature
    - 서명은 JWT의 무결성을 검증하고, 토큰이 위조되지 않았음을 확인함

### JWT를 읽는 메서드
- **`validate`** 메서드는 주로 JWT의 **유효성을 검증하고,** 필요할 경우 JWT에서 **정보를 추출하는 역할** 을 함
```java
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
```


## CORS (Cross-Origin-Resource-Sharing)
### SOP (Same-Origin-Policy)부터 알기
- 브라우저는 보안정책인 SOP를 준수한다.
- 요청이 같은(Same) 출처(Origin)에서만 허용되도록 제한하는 규칙이다.
#### 출처(Origin)란?
- Origin의 구성
    - 프로토콜
    - 호스트(도메인)
    - 포트 (명시되지 않은 경우, 기본값: HTTP는 80, HTTPS는 443)
- `http://naver.com`의 Origin
    - 프로토콜 : http://
    - 호스트(도메인) : naver.com
    - 포트 : 80

### CORS 알기
- CORS는 SOP를 우회하기 위한 방법이다.
- 서버에서 명시적으로 특정 Origin의 요청을 허용할 수 있다.
- 클라이언트가 다른 Origin으로 Resource를 요청하면:
    - 브라우저는 요청을 차단하기 전에 서버에 "이 요청을 허용할 수 있는지" 확인.
    - 서버가 CORS 설정으로 해당 Origin을 허용하면 요청이 성공.


## Spring Security의 기본 동작
1. Spring은 기본적으로 폼 기반 인증 (사용자명/비밀번호) 방식을 사용한다.
2. JWT 기반 인증 방식을 사용하면 폼 기반 인증을 우회하기 때문에 UsernamePasswordAuthenticationFilter가 실행되지 않는다.
3. 따라서 개발자가 직접 UsernamePasswordAuthenticationToken을 생성햐여 JWT에서 추출한 사용자 정보를 Spring Security에 전달해야 한다.

# Spring Security 구성 요약

## **클래스별 역할**

### **1. JwtProvider**
- **역할**: JWT 생성 및 검증.
- **주요 기능**:
  - **JWT 생성**:
    - 사용자 정보를 기반으로 유효기간, 클레임 등을 포함한 JWT 생성.
  - **JWT 검증**:
    - 클라이언트로부터 전달받은 JWT가 유효한지 검증하고, 필요한 정보를 추출.

---

### **2. JwtAuthenticationFilter**
- **역할**: 요청에서 JWT를 추출하고 검증한 후, 인증 정보를 Spring Security Context에 저장.
- **주요 기능**:
  1. 요청 헤더에서 **Authorization** 값을 읽어 JWT를 추출.
  2. `JwtProvider`를 사용하여 JWT를 검증.
  3. 검증에 성공하면, 사용자 정보를 기반으로 **`Authentication` 객체**를 생성.
  4. **Spring Security의 `SecurityContext`**에 인증 정보를 저장.
  5. 다음 필터로 요청을 전달.

- **추가 사항**:
  - `addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)`로 인해 Spring Security의 **폼 기반 인증 필터 이전**에 실행됩니다.

---

### **3. WebSecurityConfig**
- **역할**: Spring Security의 전반적인 보안 설정을 담당.
- **주요 기능**:
  1. **JWT 필터 위치 설정**:
     - `JwtAuthenticationFilter`가 폼 기반 인증(`UsernamePasswordAuthenticationFilter`) 이전에 실행되도록 구성.
  2. **CORS 설정**:
     - 모든 출처와 메서드를 허용하는 CORS 정책 적용.
  3. **CSRF 비활성화**:
     - REST API에서 불필요한 CSRF 보호 비활성화.
  4. **세션 관리**:
     - **Stateless 방식**으로 설정하여 서버가 세션을 생성하지 않도록 구성.
  5. **요청 경로 권한 설정**:
     - 특정 경로는 인증 없이 허용, 그 외 경로는 인증 필요.
  6. **예외 처리**:
     - 인증 실패 시 적절한 응답(JSON 형태) 반환.

---

## **요청 흐름**

1. 클라이언트에서 요청이 들어오면 **Spring Security 필터 체인**이 실행됩니다.
2. **`JwtAuthenticationFilter`**:
   - 요청 헤더에서 JWT를 추출하고 검증.
   - JWT가 유효하면 사용자 정보를 SecurityContext에 저장.
3. 이후 다른 필터(`UsernamePasswordAuthenticationFilter` 등)로 요청이 전달되며, 권한 검사를 포함한 추가 처리가 수행됩니다.
4. 컨트롤러에 요청이 도달.
5. 만약 JWT가 없거나 유효하지 않다면, 예외 처리 로직(`FailedAuthenticationEntryPoint`)이 실행되어 적절한 응답을 반환.

---

## **구성 요약**
- **JwtProvider**: JWT 생성 및 검증 담당.
- **JwtAuthenticationFilter**: 요청으로 오는 JWT 처리 담당.
- **WebSecurityConfig**: Spring Security 전반적인 설정 담당.
