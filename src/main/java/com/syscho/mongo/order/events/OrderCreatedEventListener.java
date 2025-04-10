package com.syscho.mongo.order.events;

import com.syscho.mongo.order.Order;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

//
//mongoTemplate.save(...)
//
//repository.save(...)
@Component
public class OrderCreatedEventListener extends AbstractMongoEventListener<Order> {

    @Override
    public void onBeforeSave(BeforeSaveEvent<Order> event) {
        System.out.println(event.getDocument());
        System.out.println("Order is going to save in DB");
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Order> event) {
        System.out.println("Order is saved in DB");
        System.out.println(event.getDocument());
    }
}
