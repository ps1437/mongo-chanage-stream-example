package com.syscho.mongo.audit;

import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/{id}")
    public AuditLog getAuditLog(@PathVariable("id") String id) throws BadRequestException {
        return auditService.getAudit(id)
                .orElseThrow(() -> new BadRequestException("No Audit log found"));
    }

    @GetMapping
    public List<AuditLog> getAuditLogs() throws BadRequestException {
        return auditService.getAllAudit();
    }
}
