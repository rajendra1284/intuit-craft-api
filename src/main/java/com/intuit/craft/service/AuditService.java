package com.intuit.craft.service;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.intuit.craft.domain.AuditLog;
import com.intuit.craft.model.AuditLogDto;

public interface AuditService {

    @Retryable(retryFor = { TimeoutException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    List<AuditLogDto> listAuditLogs();

    @Retryable(retryFor = { TimeoutException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    Future<AuditLog> saveAuditLogAsync(String userId, String objectName);

}
