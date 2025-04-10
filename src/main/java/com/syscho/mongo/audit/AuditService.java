package com.syscho.mongo.audit;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    public void audit(Document doc, String operationType) {
        AuditLog auditLog = new AuditLog();
        auditLog.setOrderData(doc);
        auditLog.setOperationType(operationType);
        auditLog.setCreatedAt(new Date());
        auditRepository.save(auditLog);
        System.out.println("Audit log saved for: " + operationType);
    }

    public Optional<AuditLog> getAudit(String id) {
        return auditRepository.findById(id);
    }

    public List<AuditLog> getAllAudit() {
        return auditRepository.findAll();
    }
}
