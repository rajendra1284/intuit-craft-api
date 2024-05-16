package com.intuit.craft.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.intuit.craft.domain.AuditLog;
import com.intuit.craft.domain.UserActionType;
import com.intuit.craft.model.AuditLogDto;
import com.intuit.craft.repository.AuditLogRepository;
import com.intuit.craft.service.AuditService;
import com.intuit.craft.util.DateUtil;

@Service
public class AuditServiceImpl implements AuditService {

    private AuditLogRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditServiceImpl.class);

    public AuditServiceImpl(AuditLogRepository repository) {
        this.repository = repository;
    }

    /**
     * save the audit log asynchronously
     * 
     * @param userId
     * @param objectName
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public Future<AuditLog> saveAuditLogAsync(String userId, String objectName) {
        LOGGER.info("Saving audit information for objectName: " + objectName + " and userId: " + userId);
        AuditLog auditLog = new AuditLog();
        auditLog.setUserId(userId);
        auditLog.setObjectName(objectName);
        auditLog.setUserAction(UserActionType.READ_METADATA.name());
        Calendar cal = Calendar.getInstance();
        auditLog.setCreateTs(new Timestamp(cal.getTimeInMillis()));
        return CompletableFuture.completedFuture(repository.save(auditLog));
    }

    /**
     * Query all the audit logs from database
     * 
     * @return List<AuditLogDto>
     */
    @Override
    public List<AuditLogDto> listAuditLogs() {
        LOGGER.info("Querying all the audit logs from database");
        List<AuditLogDto> res = new ArrayList<>();
        repository.findAll().forEach(auditLog -> {
            AuditLogDto dto = new AuditLogDto();
            dto.setUserId(auditLog.getUserId());
            dto.setObjectName(auditLog.getObjectName());
            LocalDateTime localDateTime = auditLog.getCreateTs().toLocalDateTime();
            dto.setRequestedDate(DateUtil.formatDateTime(localDateTime));
            res.add(dto);
        });
        return res;
    }

}
