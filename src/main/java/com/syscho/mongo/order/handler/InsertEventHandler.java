package com.syscho.mongo.order.handler;

import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.syscho.mongo.notification.EmailService;
import com.syscho.mongo.order.OperationType;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class InsertEventHandler implements ChangeEventHandler {

    private final EmailService emailService;

    public InsertEventHandler(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void handle(ChangeStreamDocument<Document> change) {
        Document order = change.getFullDocument();
        Objects.requireNonNull(order, "Order cannot be null");

        emailService.sendOrderConfirmation(order);
    }

    @Override
    public OperationType getType() {
        return OperationType.INSERT;
    }
}
