package com.syscho.mongo.order.events;

import com.syscho.mongo.order.Order;
import org.springframework.data.mongodb.core.mapping.event.*;
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
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Order> event) {
        System.out.println("Order is going to DELETED from DB");
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<Order> event) {
        System.out.println("Order is  DELETED from DB");
    }

    @Override
    public void onAfterConvert(AfterConvertEvent<Order> event) {
        System.out.println(" onAfterConvert");
    }

    @Override
    public void onAfterLoad(AfterLoadEvent<Order> event) {
        System.out.println(" onAfterLoad");
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Order> event) {
        System.out.println(" onBeforeConvert");
    }


}
