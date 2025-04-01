package com.cloudgov.pizza_shop.controller;

import com.cloudgov.pizza_shop.dto.ApiResponse;
import com.cloudgov.pizza_shop.exception.PizzaNotFoundException;
import com.cloudgov.pizza_shop.model.Pizza;
import com.cloudgov.pizza_shop.service.PizzaService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/pizza")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public Mono<ApiResponse<List<Pizza>>> getAllPizzas() {
        return pizzaService.getAllPizzas()
                .collectList()
                .map(pizzas -> ApiResponse.<List<Pizza>>builder()
                        .success(true)
                        .message("Pizzas retrieved successfully")
                        .data(pizzas)
                        .build())
                .onErrorResume(error -> Mono.just(ApiResponse.<List<Pizza>>builder()
                        .success(false)
                        .message("Error retrieving pizzas: " + error.getMessage())
                        .data(null)
                        .build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<Pizza>>> getPizzaById(@PathVariable String id) {
        if (id == null || id.trim().isEmpty()) {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ApiResponse.<Pizza>builder()
                            .success(false)
                            .message("Pizza ID cannot be empty")
                            .data(null)
                            .build()));
        }

        return pizzaService.getPizzaById(id)
                .map(pizza -> ResponseEntity.ok(ApiResponse.<Pizza>builder()
                        .success(true)
                        .message("Pizza retrieved successfully")
                        .data(pizza)
                        .build()))
                .onErrorResume(PizzaNotFoundException.class, ex -> Mono.just(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.<Pizza>builder()
                                .success(false)
                                .message(ex.getMessage())
                                .data(null)
                                .build())))
                .onErrorResume(Exception.class, ex -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponse.<Pizza>builder()
                                .success(false)
                                .message("Error retrieving pizza: " + ex.getMessage())
                                .data(null)
                                .build())));
    }

    @PostMapping
    public Mono<ResponseEntity<ApiResponse<Pizza>>> createPizza(@RequestBody Pizza pizza) {
        if (pizza == null) {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ApiResponse.<Pizza>builder()
                            .success(false)
                            .message("Pizza object cannot be null")
                            .data(null)
                            .build()));
        }

        if (pizza.getName() == null || pizza.getName().trim().isEmpty()) {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ApiResponse.<Pizza>builder()
                            .success(false)
                            .message("Pizza name is required")
                            .data(null)
                            .build()));
        }

        if (pizza.getPrice() == null || pizza.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ApiResponse.<Pizza>builder()
                            .success(false)
                            .message("Valid pizza price is required")
                            .data(null)
                            .build()));
        }

        return pizzaService.createPizza(pizza)
                .map(createdPizza -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(ApiResponse.<Pizza>builder()
                                .success(true)
                                .message("Pizza created successfully")
                                .data(createdPizza)
                                .build()))
                .onErrorResume(Exception.class, ex -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponse.<Pizza>builder()
                                .success(false)
                                .message("Error creating pizza: " + ex.getMessage())
                                .data(null)
                                .build())));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<Pizza>>> updatePizza(@PathVariable String id, @RequestBody Pizza pizza) {
        if (id == null || id.trim().isEmpty()) {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ApiResponse.<Pizza>builder()
                            .success(false)
                            .message("Pizza ID cannot be empty")
                            .data(null)
                            .build()));
        }

        if (pizza == null) {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ApiResponse.<Pizza>builder()
                            .success(false)
                            .message("Pizza object cannot be null")
                            .data(null)
                            .build()));
        }

        if (pizza.getName() == null || pizza.getName().trim().isEmpty()) {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ApiResponse.<Pizza>builder()
                            .success(false)
                            .message("Pizza name is required")
                            .data(null)
                            .build()));
        }

        if (pizza.getPrice() == null || pizza.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ApiResponse.<Pizza>builder()
                            .success(false)
                            .message("Valid pizza price is required")
                            .data(null)
                            .build()));
        }

        return pizzaService.updatePizza(id, pizza)
                .map(updatedPizza -> ResponseEntity.ok(ApiResponse.<Pizza>builder()
                        .success(true)
                        .message("Pizza updated successfully")
                        .data(updatedPizza)
                        .build()))
                .onErrorResume(PizzaNotFoundException.class, ex -> Mono.just(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.<Pizza>builder()
                                .success(false)
                                .message(ex.getMessage())
                                .data(null)
                                .build())))
                .onErrorResume(Exception.class, ex -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponse.<Pizza>builder()
                                .success(false)
                                .message("Error updating pizza: " + ex.getMessage())
                                .data(null)
                                .build())));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<Void>>> deletePizza(@PathVariable String id) {
        if (id == null || id.trim().isEmpty()) {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ApiResponse.<Void>builder()
                            .success(false)
                            .message("Pizza ID cannot be empty")
                            .data(null)
                            .build()));
        }

        return pizzaService.deletePizza(id)
                .then(Mono.just(ResponseEntity
                        .ok(ApiResponse.<Void>builder()
                                .success(true)
                                .message("Pizza deleted successfully")
                                .data(null)
                                .build())))
                .onErrorResume(PizzaNotFoundException.class, ex -> Mono.just(ResponseEntity
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
                                .message("Error deleting pizza: " + ex.getMessage())
                                .data(null)
                                .build())));
    }

}
