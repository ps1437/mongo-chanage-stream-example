package com.syscho.mongo.notification;

import org.bson.Document;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendOrderConfirmation(Document order) {
        String email = order.getString("email");
        System.out.println("Sending order confirmation to " + email);
    }
}
