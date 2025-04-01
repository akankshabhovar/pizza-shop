package com.cloudgov.pizza_shop.service;

import com.cloudgov.pizza_shop.model.Order;
import com.cloudgov.pizza_shop.repository.OrderRepository;
import com.cloudgov.pizza_shop.enums.OrderStatusEnum;
import com.cloudgov.pizza_shop.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    public Mono<Order> createOrder(Order order) {
        order.setOrderTime(LocalDateTime.now());
        order.setLastUpdated(LocalDateTime.now());
        order.setStatus(OrderStatusEnum.PLACED);
        return orderRepository.save(order);
    }

    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Mono<Order> getOrderById(String id) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new OrderNotFoundException(id)));
    }

    public Mono<Order> updateOrderStatus(String id, OrderStatusEnum status) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new OrderNotFoundException(id)))
                .flatMap(existingOrder -> {
                    existingOrder.setStatus(status);
                    existingOrder.setLastUpdated(LocalDateTime.now());
                    return orderRepository.save(existingOrder);
                });
    }

    public Mono<Void> deleteOrder(String id) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new OrderNotFoundException(id)))
                .flatMap(order -> orderRepository.deleteById(id));
    }
}
