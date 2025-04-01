package com.cloudgov.pizza_shop.repository;

import com.cloudgov.pizza_shop.enums.OrderStatusEnum;
import com.cloudgov.pizza_shop.model.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order, String> {
    Flux<Order> findByStatus(OrderStatusEnum status);
    Flux<Order> findByCustomerName(String customerName);
}