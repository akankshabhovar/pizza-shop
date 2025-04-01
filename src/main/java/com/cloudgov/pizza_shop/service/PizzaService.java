package com.cloudgov.pizza_shop.service;

import com.cloudgov.pizza_shop.model.Pizza;
import com.cloudgov.pizza_shop.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PizzaService {
    
    @Autowired
    private PizzaRepository pizzaRepository;

    public Flux<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    public Mono<Pizza> getPizzaById(String id) {
        return pizzaRepository.findById(id);
    }

    public Mono<Pizza> createPizza(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public Mono<Pizza> updatePizza(String id, Pizza pizza) {
        return pizzaRepository.findById(id)
            .flatMap(existingPizza -> {
                pizza.setId(id);
                return pizzaRepository.save(pizza);
            });
    }

    public Mono<Void> deletePizza(String id) {
        return pizzaRepository.deleteById(id);
    }
}
