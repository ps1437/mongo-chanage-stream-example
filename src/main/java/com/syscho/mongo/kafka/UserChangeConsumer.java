package com.syscho.mongo.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserChangeConsumer {

    @KafkaListener(topics = "mongo.testdb.user", groupId = "mongo-group")
    public void consume(String message) {
        System.out.println("Received MongoDB Change: " + message);
    }
}
