package com;

import com.model.Order;
import com.model.vehicle.Auto;
import com.model.vehicle.Bus;
import com.model.vehicle.Vehicle;
import com.repository.AutoRepository;
import com.repository.BusRepository;
import com.service.AutoService;
import com.service.BusService;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class Main {
    private static final AutoService AUTO_SERVICE =
            new AutoService(new AutoRepository());
    private static final BusService BUS_SERVICE =
            new BusService(new BusRepository());

    public static void main(String[] args) {
        final List<Auto> autos = AUTO_SERVICE.createAndSaveAutos(2);
        final List<Bus> buses = BUS_SERVICE.createAndSave(2);

        final Order order = new Order(autos.size());
        order.addAll(autos);
        System.out.println("Add all");
        System.out.println(order);
        System.out.println();

        System.out.println("Add index");
        final Auto auto = AUTO_SERVICE.createAndSaveAutos(1).get(0);
        order.add(0, auto);
        order.add(3, auto);
        System.out.println(order);
        System.out.println();

        System.out.println("Get index");
        final Vehicle vehicle = order.get(1);
        System.out.println(vehicle);
        System.out.println();

        System.out.println("Remove index");
        System.out.println(order.remove(1));
        System.out.println(order);
        System.out.println();


        System.out.println("Index of");
        System.out.println(order.indexOf(vehicle));
        System.out.println();

        System.out.println(order);
        System.out.println();

        System.out.println("Set index");
        order.set(0, vehicle);
        System.out.println(order);
    }
}
