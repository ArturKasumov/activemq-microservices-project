package com.arturk.gateway.config.filter;

import com.arturk.gateway.service.SidValidationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class SidAuthenticationFilter extends OncePerRequestFilter {

    private final SidValidationService sidValidationService;

    public SidAuthenticationFilter(SidValidationService sidValidationService) {
        this.sidValidationService = sidValidationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String sid = request.getHeader("sid");

        if (sid == null || !sidValidationService.isValid(sid)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid SID");
            return;
        }

        filterChain.doFilter(request, response);
    }
}