package com.cloudgov.pizza_shop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.cloudgov.pizza_shop.model.Pizza;

@Repository
public interface PizzaRepository extends MongoRepository<Pizza, String> {

    
}
