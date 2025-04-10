package com.syscho.mongo.audit;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditRepository extends MongoRepository<AuditLog, String> {

}
