package com.jonas.userservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {

    public static final String SECRET = "mySecretsecretmySecretsecretmySecretsecretmySecretsecret";
    public static final String AUTH_HEADER = "Authorization";
    public static final long EXPIRATION_ACCESS_TOKEN = 1000 * 60 * 60 * 24 * 2; // 2 hours
    public static final long EXPIRATION_REFRESH_TOKEN = 1000 * 60 * 60 * 24 * 2; // 2 days

    public static Jws<Claims> extractClaimsFromToken(HttpServletRequest request) {
        String authorization = request.getHeader(AUTH_HEADER);
        String token = authorization.replace("Bearer ", "");
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token);
    }

    public static String issueToken(Authentication authResult, Long expiration) {
        return Jwts.builder()
                .setSubject(authResult.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(JwtUtil.SECRET.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public static String extractUsernameFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader(AUTH_HEADER);
        String token = authorization.replace("Bearer ", "");
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token);

        return claimsJws.getBody().getSubject();
    }

    public static void refreshToken(HttpServletResponse response, String username) {
        String refreshedToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_REFRESH_TOKEN))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                .compact();

        response.setHeader(AUTH_HEADER, refreshedToken);
    }
}
