package com.example.demo.auth.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.auth.entities.Session;
import com.example.demo.auth.services.SessionService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestFilter implements Filter{

    @Autowired
    private SessionService sessionService;

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;

        String path = request.getRequestURI();
        if (path.equals("/auth/signin") || path.equals("/auth/signup")) {
            arg2.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Bearer");
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token de autentificação invalido");
            return;
        }

        Optional<Session> session = sessionService.getSession(token);

        if (!session.isPresent() || sessionService.hasSessionExpired(session.get())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token de autentificação invalido");
            return;
        }

        arg2.doFilter(request, response);
    }

}
