package com.cloudgov.pizza_shop.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.cloudgov.pizza_shop.enums.OrderStatusEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private List<Pizza> pizzas;
    private String customerName;
    private String customerAddress;
    private String phoneNumber;
    private BigDecimal totalAmount;
    private OrderStatusEnum status;
    private LocalDateTime orderTime;
    private LocalDateTime lastUpdated;
}
