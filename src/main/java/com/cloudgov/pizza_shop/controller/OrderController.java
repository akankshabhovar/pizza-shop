package com.cloudgov.pizza_shop.controller;

import com.cloudgov.pizza_shop.model.Order;
import com.cloudgov.pizza_shop.service.OrderService;
import com.cloudgov.pizza_shop.dto.ApiResponse;
import com.cloudgov.pizza_shop.enums.OrderStatusEnum;
import com.cloudgov.pizza_shop.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Mono<ResponseEntity<ApiResponse<Order>>> createOrder(@RequestBody Order order) {
        if (order == null) {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ApiResponse.<Order>builder()
                            .success(false)
                            .message("Order cannot be null")
                            .data(null)
                            .build()));
        }

        if (order.getPizzas() == null || order.getPizzas().isEmpty()) {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ApiResponse.<Order>builder()
                            .success(false)
                            .message("Order must contain at least one pizza")
                            .data(null)
                            .build()));
        }

        return orderService.createOrder(order)
                .map(createdOrder -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(ApiResponse.<Order>builder()
                                .success(true)
                                .message("Order created successfully")
                                .data(createdOrder)
                                .build()))
                .onErrorResume(Exception.class, ex -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponse.<Order>builder()
                                .success(false)
                                .message("Error creating order: " + ex.getMessage())
                                .data(null)
                                .build())));
    }

    @GetMapping
    public Mono<ResponseEntity<ApiResponse<java.util.List<Order>>>> getAllOrders() {
        return orderService.getAllOrders()
                .collectList()
                .map(orders -> ResponseEntity.ok(ApiResponse.<java.util.List<Order>>builder()
                        .success(true)
                        .message("Orders retrieved successfully")
                        .data(orders)
                        .build()))
                .onErrorResume(Exception.class, ex -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponse.<java.util.List<Order>>builder()
                                .success(false)
                                .message("Error retrieving orders: " + ex.getMessage())
                                .data(null)
                                .build())));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<Order>>> getOrderById(@PathVariable String id) {
        if (id == null || id.trim().isEmpty()) {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ApiResponse.<Order>builder()
                            .success(false)
                            .message("Order ID cannot be empty")
                            .data(null)
                            .build()));
        }

        return orderService.getOrderById(id)
                .map(order -> ResponseEntity.ok(ApiResponse.<Order>builder()
                        .success(true)
                        .message("Order retrieved successfully")
                        .data(order)
                        .build()))
                .onErrorResume(OrderNotFoundException.class, ex -> Mono.just(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.<Order>builder()
                                .success(false)
                                .message(ex.getMessage())
                                .data(null)
                                .build())))
                .onErrorResume(Exception.class, ex -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponse.<Order>builder()
                                .success(false)
                                .message("Error retrieving order: " + ex.getMessage())
                                .data(null)
                                .build())));
    }

    @PutMapping("/{id}/status")
    public Mono<ResponseEntity<ApiResponse<Order>>> updateOrderStatus(
            @PathVariable String id,
            @RequestBody OrderStatusEnum status) {
        if (id == null || id.trim().isEmpty()) {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ApiResponse.<Order>builder()
                            .success(false)
                            .message("Order ID cannot be empty")
                            .data(null)
                            .build()));
        }

        if (status == null) {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ApiResponse.<Order>builder()
                            .success(false)
                            .message("Order status cannot be null")
                            .data(null)
                            .build()));
        }

        return orderService.updateOrderStatus(id, status)
                .map(updatedOrder -> ResponseEntity.ok(ApiResponse.<Order>builder()
                        .success(true)
                        .message("Order status updated successfully")
                        .data(updatedOrder)
                        .build()))
                .onErrorResume(OrderNotFoundException.class, ex -> Mono.just(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.<Order>builder()
                                .success(false)
                                .message(ex.getMessage())
                                .data(null)
                                .build())))
                .onErrorResume(Exception.class, ex -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponse.<Order>builder()
                                .success(false)
                                .message("Error updating order status: " + ex.getMessage())
                                .data(null)
                                .build())));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<Void>>> deleteOrder(@PathVariable String id) {
        if (id == null || id.trim().isEmpty()) {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ApiResponse.<Void>builder()
                            .success(false)
                            .message("Order ID cannot be empty")
                            .data(null)
                            .build()));
        }

        return orderService.deleteOrder(id)
                .then(Mono.just(ResponseEntity.ok(ApiResponse.<Void>builder()
                        .success(true)
                        .message("Order deleted successfully")
                        .data(null)
                        .build())))
                .onErrorResume(OrderNotFoundException.class, ex -> Mono.just(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.<Void>builder()
                                .success(false)
                                .message(ex.getMessage())
                                .data(null)
                                .build())))
                .onErrorResume(Exception.class, ex -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponse.<Void>builder()
                                .success(false)
                                .message("Error deleting order: " + ex.getMessage())
                                .data(null)
                                .build())));
    }
}
