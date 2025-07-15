package com.libronote.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        long startedTime = System.currentTimeMillis();

        log.info("=================================================>>");
        log.info("URL : {} ",request.getRequestURL());
        log.info("HTTP_METHOD : {}", request.getMethod());
        log.info("URI : {}", request.getRequestURI());
        log.info("QUERY : {}", request.getQueryString());
        log.info("CONTENT_TYPE : {}", request.getContentType());
        log.info("=================================================>>");

        filterChain.doFilter(servletRequest, servletResponse);

        long endedTime = System.currentTimeMillis() - startedTime;

        log.info("<<=================================================");
        log.info("RESPONSE_ENDED_TIME : {}", endedTime);
        log.info("RESPONSE_STATUS : {}", response.getStatus());
        log.info("RESPONSE_CONTENT_TYPE : {}", response.getContentType());
        log.info("<<=================================================");
    }
}
