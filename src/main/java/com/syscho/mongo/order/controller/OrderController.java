package com.syscho.mongo.order.controller;

import com.syscho.mongo.order.Order;
import com.syscho.mongo.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping
    private List<Order> getOrder() {
        return repository.findAll();
    }

    @PostMapping
    public Order addOrder(@RequestBody Order order) {
        return repository.save(order);
    }

    @PatchMapping("/{id}")
    public void updateFields(@PathVariable String id, @RequestBody Map<String, Object> orderUpdate) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        orderUpdate.forEach(update::set);
        mongoTemplate.updateFirst(query, update, Order.class);
    }


    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id) {
        repository.deleteById(id);
    }
}
