package com.cloudgov.pizza_shop.exception;

public class PizzaNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public PizzaNotFoundException(String id) {
        super("Pizza not found with id: " + id);
    }
}