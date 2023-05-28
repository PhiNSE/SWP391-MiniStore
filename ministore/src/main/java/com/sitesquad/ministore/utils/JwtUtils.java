package com.sitesquad.ministore.utils;

import com.sitesquad.ministore.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

/**
 *
 * @author ADMIN
 */
@Component
public class JwtUtils {
    private static String secretKey = "123456";
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
        
        claims.put("role", user.getRoles().getName());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        // generate jwt
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
    
    
        public Jws<Claims> verify(String token){
        try {
//        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
         Jws<Claims> jwt = Jwts.parser()                  
                .setSigningKey(secretKey)                    
                .parseClaimsJws(token);
                return jwt;
            } catch (SignatureException jwtException) {
        jwtException.printStackTrace();
        return null;
     }
    }
    
 
    public static void main(String[] args) {
        Jws<Claims> jwt = Jwts.parser()                  
                .setSigningKey(secretKey)                    
                .parseClaimsJws("eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiIxIiwiaWF0IjoxNjg1MjQ1NjQ3LCJleHAiOjE2ODUyNDkyNDcsInJvbGUiOiJhZG1pbiIsIm5hbWUiOiJhZG1pbiIsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIn0.y1w5-uFAqZO_D3xjv-nVxgflW9m1KH6OzBjCAoASt_fIUarlFc0FLxXYngFZpkrAqqFuPjX3lf1xM4V_3gQpfw");
        
        System.out.println(jwt);
    }
}
