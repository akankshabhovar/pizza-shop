package com.cloudgov.pizza_shop.exception;

public class OrderNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public OrderNotFoundException(String id) {
        super("Order not found with id: " + id);
    }
}