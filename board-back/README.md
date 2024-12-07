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