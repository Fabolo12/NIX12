package com.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Bus extends Vehicle {
    private int countPeople;

    public Bus(String model, Manufacturer manufacturer, BigDecimal price, int countPeople, int count) {
        super(model, manufacturer, price, count);
        this.countPeople = countPeople;
    }
}
