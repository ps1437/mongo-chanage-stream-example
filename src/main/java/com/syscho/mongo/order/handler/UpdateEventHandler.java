package com.syscho.mongo.order.handler;

import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.UpdateDescription;
import com.syscho.mongo.audit.AuditService;
import com.syscho.mongo.order.OperationType;
import org.bson.BsonDocument;
import org.bson.Document;
import org.springframework.stereotype.Component;

@Component
public class UpdateEventHandler implements ChangeEventHandler {

    private final AuditService auditService;

    public UpdateEventHandler(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    public void handle(ChangeStreamDocument<Document> change) {
        System.out.println("Updated to Audit table");
        BsonDocument documentKey = change.getDocumentKey();
        Object id = documentKey != null ? documentKey.get("_id") : null;

        UpdateDescription updateDescription = change.getUpdateDescription();
        Document updatedFields = new Document();
        if (updateDescription != null) {
            updatedFields.putAll(updateDescription.getUpdatedFields());
        } else if (change.getFullDocument() != null) {
            updatedFields.putAll(change.getFullDocument());
        }

        if (id != null) {
            updatedFields.put("_id", change.getDocumentKey().getObjectId("_id").getValue().toHexString());
        }
        auditService.audit(updatedFields, OperationType.UPDATE.name());
    }

    @Override
    public OperationType getType() {
        return OperationType.UPDATE;
    }
}
