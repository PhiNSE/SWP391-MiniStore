package com.sitesquad.ministore.config;

import com.sitesquad.ministore.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        String auth = request.getHeader("token");

        if(!request.getRequestURI().contains("login")) {
            jwtUtils.verify(auth);
        }
        System.out.println("PRE-HANDLE");
        return super.preHandle(request,response,handler);
    }
}
