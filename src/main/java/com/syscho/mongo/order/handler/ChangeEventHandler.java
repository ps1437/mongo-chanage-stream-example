package com.syscho.mongo.order.handler;

import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.syscho.mongo.order.OperationType;
import org.bson.Document;

public interface ChangeEventHandler {
    void handle(ChangeStreamDocument<Document> change);

    OperationType getType();
}
