package com.intuit.craft.exception;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.intuit.craft.model.StatusDto;
import com.intuit.craft.service.impl.AuditServiceImpl;
import com.intuit.craft.util.JSONUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditServiceImpl.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
            throws IOException, ServletException {
        handle(request, response, ex);
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, Exception ex)
            throws IOException, ServletException {
        LOGGER.error(ex.getMessage(), ex);
        StatusDto statusDto = new StatusDto(ex.getMessage(), HttpStatus.FORBIDDEN);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().write(JSONUtil.serialize(statusDto));
    }

}