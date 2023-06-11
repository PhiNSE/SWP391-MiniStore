package com.sitesquad.ministore.utils;

import com.sitesquad.ministore.exception.AccessDeniedException;
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
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author ADMIN
 */
@Component
@CrossOrigin
public class JwtUtils {
    private static String secretKey = "123456";
    private static long expriryDuration = 60*60;
    
    public String generateJWT(User user){
        
        long milliTime = System.currentTimeMillis();
        long expiryTime = milliTime + expriryDuration * 1000;
        
        Date issuedAt = new Date(milliTime);
        Date expiryAt = new Date(expiryTime);
        
        Claims claims = Jwts.claims()
                .setIssuer(user.getUserId().toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expiryAt);
        claims.put("id",user.getUserId());
        claims.put("role", user.getRole().getName());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        // generate jwt
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }


        @CrossOrigin
        public Claims  verify(String token) throws Exception{
        try {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        return claims;

//         Jws<Claims> jwt = Jwts.parser()token
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token);
//                return jwt;

            } catch (Exception e) {
                System.out.println("Error token" + token);
                throw new AccessDeniedException("Access Denied");
     }
    }
    
 
    public static void main(String[] args) {
        Jws<Claims> jwt = Jwts.parser()                  
                .setSigningKey(secretKey)                    
                .parseClaimsJws("eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiIyIiwiaWF0IjoxNjg2MzA1MDQzLCJleHAiOjE2ODYzMDg2NDMsInJvbGUiOiJhZG1pbiIsIm5hbWUiOiJhZG1pbiIsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIn0.z09_PVNXOWIUYRUndZ68UIJIVx5lh9ytJxC5vCoaToxSwHtE3d1oGNvlPMQll9rCcS25swkBud5bewDwsHRWRA");
        
        System.out.println(jwt);
    }
}
