package com.cloudgov.pizza_shop.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    PLACED(1),
    PREPARING(2),
    READY_FOR_DELIVERY(3),
    OUT_FOR_DELIVERY(4),
    DELIVERED(5),
    CANCELLED(6);

    private final int value;

    OrderStatusEnum(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static OrderStatusEnum fromValue(int value) throws Exception {
        for (OrderStatusEnum status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new Exception("Invalid order status value: " + value);
    }
}
