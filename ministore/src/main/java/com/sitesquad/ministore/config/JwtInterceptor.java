package com.sitesquad.ministore.config;

import com.sitesquad.ministore.model.RequestMeta;
import com.sitesquad.ministore.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
@CrossOrigin
public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtils jwtUtils;

    private RequestMeta requestMeta;

    public JwtInterceptor(RequestMeta requestMeta){
        this.requestMeta=requestMeta;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());

        String auth = request.getHeader("token");

        if(request.getRequestURI().contains("login")) {
            Claims claims = jwtUtils.verify(auth);

            requestMeta.setUserId(Long.valueOf(claims.getIssuer()));
            requestMeta.setName(claims.get("name").toString());
            requestMeta.setRole(claims.get("role").toString());
            requestMeta.setEmail(claims.get("email").toString());
        }
        System.out.println("PRE-HANDLE");
        return super.preHandle(request,response,handler);
    }
}
