package com.security.token;

import com.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {


    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

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
            logger.error("Token expired", expEx.getMessage());
        } catch (UnsupportedJwtException unsEx) {
            logger.error("Unsupported jwt", unsEx.getMessage());
        } catch (MalformedJwtException mjEx) {
            logger.error("Malformed jwt", mjEx.getMessage());
        } catch (SignatureException sEx) {
            logger.error("Invalid signature", sEx.getMessage());
        } catch (Exception e) {
            logger.error("invalid token", e.getMessage());
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
