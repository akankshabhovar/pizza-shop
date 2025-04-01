# Pizza Shop API

A reactive Spring Boot application for managing a pizza shop's orders and menu.

## Architecture Overview

```mermaid
graph TD
    Client[Client] --> |HTTP Requests| API[Pizza Shop API]
    API --> |Reactive Operations| MongoDB[(MongoDB)]
    
    subgraph Pizza Shop API
        PizzaController[Pizza Controller]
        OrderController[Order Controller]
        PizzaService[Pizza Service]
        OrderService[Order Service]
        Repository[Repositories]
    end

    PizzaController --> PizzaService
    OrderController --> OrderService
    PizzaService --> Repository
    OrderService --> Repository
