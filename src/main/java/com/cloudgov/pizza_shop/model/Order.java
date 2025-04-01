package com.cloudgov.pizza_shop.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "order")
public class Order {
    @Id
    private String orderId;
    private List<Pizza> pizzas;
    private String orderStatus;
    private Date timestamp;
}
