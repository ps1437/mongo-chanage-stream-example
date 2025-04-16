package com.syscho.mongo.order.changestream;

import com.hazelcast.core.HazelcastInstance;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.changestream.FullDocument;
import com.syscho.mongo.order.OperationType;
import com.syscho.mongo.order.handler.ChangeEventHandler;
import jakarta.annotation.PostConstruct;
import org.bson.BsonDocument;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OrderChangeStreamListener {

    private final MongoClient mongoClient;
    private final HazelcastInstance hazelcastInstance;
    public static final String RESUME_TOKEN_MAP = "resumeTokens";
    private final Map<OperationType, ChangeEventHandler> handlerMap;

    public OrderChangeStreamListener(MongoClient mongoClient, List<ChangeEventHandler> changeEventHandlerList, HazelcastInstance hazelcastInstance) {
        this.mongoClient = mongoClient;
        this.hazelcastInstance = hazelcastInstance;
        this.handlerMap = Collections.unmodifiableMap(
                changeEventHandlerList.stream()
                        .collect(Collectors.toMap(ChangeEventHandler::getType, handler -> handler))
        );
    }

    @Async
    @PostConstruct
    public void init() {
        new Thread(this::listenToChanges).start();
    }

    private void listenToChanges() {
        MongoDatabase db = mongoClient.getDatabase("ecommerce");
        var collection = db.getCollection("orders");
        com.hazelcast.map.IMap<Object, Object> tokenMap = hazelcastInstance.getMap(RESUME_TOKEN_MAP);

        Object resumeTokenObj = tokenMap.get("order-stream");

        var changeStreamIterable = collection.watch()
                .fullDocument(FullDocument.UPDATE_LOOKUP);

        if (resumeTokenObj != null) {
            BsonDocument resumeToken = (BsonDocument) resumeTokenObj;
            changeStreamIterable.resumeAfter(resumeToken);
        }
        changeStreamIterable.forEach(change -> {
            try {
                OperationType operationType = OperationType.valueOf(change.getOperationType().getValue().toUpperCase());
                var handler = handlerMap.get(operationType);

                if (handler != null) {
                    handler.handle(change);  // Handle the change
                } else {
                    System.out.println("No handler for operationType: " + operationType);
                }

                BsonDocument newToken = change.getResumeToken();
                System.out.println(newToken);
                tokenMap.put("order-stream", newToken);

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error processing change event: " + e.getMessage());
            }

        });
    }
}
