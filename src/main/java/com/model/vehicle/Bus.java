package com.model.vehicle;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Comparator;

@Getter
@Setter
public class Bus extends Vehicle {
    private int countPeople;

    public Bus(String model, Manufacturer manufacturer, BigDecimal price, int countPeople, int count) {
        super(model, manufacturer, price, count);
        this.countPeople = countPeople;
    }

    public static class BusComparator implements Comparator<Bus> {
        @Override
        public int compare(Bus o1, Bus o2) {
            return o1.getId().compareTo(o2.getId());
        }
    }
}
