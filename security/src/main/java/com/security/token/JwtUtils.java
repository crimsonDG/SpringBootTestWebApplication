package com.security.token;

import com.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    //Should be hidden
    private String jwtSecret = "someSecret";

    //Should be hidden
    private int jwtExpirationMs = 86400000;

    public String generateToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx.getMessage());
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx.getMessage());
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx.getMessage());
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx.getMessage());
        } catch (Exception e) {
            log.error("invalid token", e.getMessage());
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
