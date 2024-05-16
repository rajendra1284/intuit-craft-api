package com.intuit.craft.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.craft.exception.CraftException;
import com.intuit.craft.model.ObjectMetadata;
import com.intuit.craft.service.AuditService;
import com.intuit.craft.service.S3Service;

@RestController
@RequestMapping("/api/v1")
@EnableMethodSecurity
public class S3Controller {

    private S3Service s3Service;

    private AuditService auditService;

    private static final Logger LOGGER = LoggerFactory.getLogger(S3Controller.class);

    public S3Controller(S3Service s3Service, AuditService auditService) {
        this.s3Service = s3Service;
        this.auditService = auditService;
    }

    /**
     * Get S3 metadata for the given object.
     * 
     * @param name
     * @return ObjectMetadata
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('user', 'admin')")
    @GetMapping(path = "/object_metadata/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectMetadata getObjectMetadata(@PathVariable("name") String name) {
        if (name == null || "".equals(name.trim())) {
            throw new CraftException("Object name is null or empty", HttpStatus.BAD_REQUEST);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        LOGGER.info("Received /object_metadata request from userId: " + userId + " for objectName: " + name);
        ObjectMetadata metadata = s3Service.getObjectMetadata(name, userId);
        auditService.saveAuditLogAsync(userId, name);
        return metadata;
    }

}
