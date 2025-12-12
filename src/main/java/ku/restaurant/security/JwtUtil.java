package ku.restaurant.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import javax.management.relation.Role;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class JwtUtil {


    @Value("${jwt.secret}")
    private String jwtSecret;


    @Value("${jwt.expiration}")
    private int jwtExpirationMs;


    private SecretKey key;

    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();


    // Initializes the key after the class is instantiated and
    // the jwtSecret is injected, preventing the repeated creation
    // of the key and enhancing performance
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
    // Generate JWT token
    public String generateToken(String username, String roles) {
        String token = Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
        tokenStore.put(token, username);
        return token;
    }
    // Get username from JWT token
    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key).build()
                    .parseSignedClaims(token)
                    .getPayload().getSubject();
        } catch (JwtException e) {
            throw e;
        }
    }

    public String getRoleFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.get("roles", String.class);
        } catch (JwtException e) {
            throw e;
        }
    }
    // Validate JWT token
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key).build()
                    .parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new JwtException("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            throw new JwtException("JWT token is expired", e);
        } catch (UnsupportedJwtException e) {
            throw new JwtException("JWT token is unsupported", e);
        } catch (IllegalArgumentException e) {
            throw new JwtException("JWT claims string is empty", e);
        }
    }

    public void invalidateToken(String token) {
        tokenStore.remove(token);
    }
}

