package com.sitesquad.ministore.utils;

import com.sitesquad.ministore.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 *
 * @author ADMIN
 */
@Component
public class JwtUtils {
    private static String secretKey = "tamDepTrai";
    private static long expriryDuration = 60*60;
    
    public String generateJWT(User user){
        
        long milliTime = System.currentTimeMillis();
        long expiryTime = milliTime + expriryDuration * 1000;
        
        Date issuedAt = new Date(milliTime);
        Date expiryAt = new Date(expiryTime);
        
        Claims claims = Jwts.claims()
                .setIssuer(user.getId().toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expiryAt);
        
        claims.put("role id", user.getRoles().getName());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        // generate jwt
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
    
    
}
