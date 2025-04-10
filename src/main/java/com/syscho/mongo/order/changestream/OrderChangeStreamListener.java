package com.syscho.mongo.order.changestream;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.changestream.FullDocument;
import com.syscho.mongo.order.OperationType;
import com.syscho.mongo.order.handler.ChangeEventHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OrderChangeStreamListener {

    private final MongoClient mongoClient;

    private Map<OperationType, ChangeEventHandler> handlerMap;

    public OrderChangeStreamListener(MongoClient mongoClient, List<ChangeEventHandler> changeEventHandlerList) {
        this.mongoClient = mongoClient;
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

        try {
            collection.watch()
                    .fullDocument(FullDocument.UPDATE_LOOKUP)
                    .forEach(change -> {
                        OperationType operationType = OperationType.valueOf(change.getOperationType().getValue().toUpperCase());
                        var handler = handlerMap.get(operationType);
                        if (handler != null) {
                            handler.handle(change);
                        } else {
                            System.out.println("No handler for operationType: " + operationType);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(" Error while listening to changes: " + e.getMessage());
        }

    }
}
