package com.chatop.api.auth;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.chatop.api.model.DBUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtUtil {


    // @Value("${app.jwt.secret}")
    // private String SECRET_KEY;

    private final String secret_key = "mysecretkey";
    private long accessTokenValidity = 60 * 60 * 1000; // One hour validity

    private JwtParser jwtParser;

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    // Create JWT

    private void setJwtParser() {
        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }

    public String createToken(DBUser user) {

        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("name", user.getName());

        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
    }

    // Check JWT

    private Claims parseJwtClaims(String token) {
        setJwtParser();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    // Other methods

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }

    public String getEmail(Claims claims) {
        return claims.getSubject();
    }

    // private List<String> getRoles(Claims claims) {
    //     return (List<String>) claims.get("roles");
    // }

}
