package com.intuit.craft.repository;

import org.springframework.data.repository.CrudRepository;

import com.intuit.craft.domain.AuditLog;

public interface AuditLogRepository extends CrudRepository<AuditLog, Long> {

}
