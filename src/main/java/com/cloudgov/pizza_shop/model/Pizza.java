package com.cloudgov.pizza_shop.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "pizza")
public class Pizza {
    @Id
    private String id;
    private String name;
    private String description;
    private List<String> toppings;
    private String size;
    private Integer price;
}
