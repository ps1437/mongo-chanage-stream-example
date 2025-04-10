package com.syscho.mongo.audit;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document("audit")
public class AuditLog {

    @Id
    private String id;

    private Object orderData;

    private String operationType;

    @Indexed(expireAfter = "60d") // TTL  - 60 days
    private Date createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getOrderData() {
        return orderData;
    }

    public void setOrderData(Object orderData) {
        this.orderData = orderData;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
