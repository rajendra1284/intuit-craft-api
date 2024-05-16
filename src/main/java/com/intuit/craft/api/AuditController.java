package com.intuit.craft.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.craft.model.AuditLogDto;
import com.intuit.craft.service.AuditService;

@RestController
@RequestMapping("/api/v1")
@EnableMethodSecurity
public class AuditController {

    private AuditService auditService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditController.class);

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    /**
     * Get the list of audit logs from database.
     * 
     * @return List<AuditLogDto>
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping(path = "/audit_log", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AuditLogDto> getAuditLogs(@AuthenticationPrincipal Jwt jwt) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        LOGGER.info("Received /audit_log request from userId: " + userId);
        return auditService.listAuditLogs();
    }

}
