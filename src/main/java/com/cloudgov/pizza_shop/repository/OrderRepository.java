package com.cloudgov.pizza_shop.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.cloudgov.pizza_shop.model.Order;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order, String>{
 
}