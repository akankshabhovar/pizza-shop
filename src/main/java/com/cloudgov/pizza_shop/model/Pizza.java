package com.cloudgov.pizza_shop.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "pizzas")
public class Pizza {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private List<String> toppings;
    private boolean vegetarian;
    private String imageUrl;
    private Date createdAt;
    private Date updatedAt;
}
