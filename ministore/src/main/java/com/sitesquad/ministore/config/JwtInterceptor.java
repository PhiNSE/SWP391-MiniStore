package com.sitesquad.ministore.config;

import com.sitesquad.ministore.dto.RequestMeta;
import com.sitesquad.ministore.exception.AccessDeniedException;
import com.sitesquad.ministore.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtils jwtUtils;

    private RequestMeta requestMeta;

    public JwtInterceptor(RequestMeta requestMeta){
        this.requestMeta=requestMeta;
    }


    @CrossOrigin
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (request.getMethod().equals("OPTIONS")) {
//            response.setHeader("Access-Control-Allow-Origin", "*");
//            response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD, OPTIONS");
//            response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization, X-Forwarded-For");
//            response.setHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
//            response.setHeader("Access-Control-Allow-Credentials", "true");
//            response.setHeader("Vary", "Origin, Access-Control-Request-Method, Access-Control-Request-Headers");
//            return true;
//        }


            System.out.println("URI: " + request.getRequestURI());
            System.out.println("Method: "+request.getMethod());
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization, X-Forwarded-For");
            response.setHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Vary", "Origin, Access-Control-Request-Method, Access-Control-Request-Headers");




        if (!isLoginApi(request)) {
            String auth = request.getHeader("Authorization");
            if(auth == null || auth.isEmpty()){
                throw new AccessDeniedException("Missing authorize");
            }
            if (auth != null) {
                Claims claims = jwtUtils.verify(auth);
                requestMeta.setUserId(Long.valueOf(claims.getIssuer()));
                requestMeta.setName(claims.get("name").toString());
                requestMeta.setRole(claims.get("role").toString());
                requestMeta.setEmail(claims.get("email").toString());

                System.out.println("Id: " + requestMeta.getUserId());
                System.out.println("Name: " + requestMeta.getName());
                System.out.println("Role: " + requestMeta.getRole());
                System.out.println("Ema: " + requestMeta.getEmail());

                System.out.println("PRE-HANDLE");
            }
        }
        return true;

    }

    private boolean isLoginApi(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.contains("/login") || requestURI.contains("/home") || requestURI.contains("/error") || requestURI.contains("/vnpay-payment");
    }
}
