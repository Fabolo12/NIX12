package com.model;

import com.model.vehicle.Vehicle;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Order {
    private final String id;

    private Vehicle[] vehicles;

    private int size;

    private BigDecimal totalPrice;

    public Order() {
        this(10);
    }

    public Order(int capacity) {
        id = UUID.randomUUID().toString();
        size = 0;
        totalPrice = BigDecimal.ZERO;
        vehicles = new Vehicle[capacity];
    }

    public void add(Vehicle vehicle) {
        for (int i = 0; i < vehicles.length; i++) {
            if (vehicles[i] == null) {
                addNewValue(i, vehicle);
                return;
            }
        }
        addNewValue(vehicles.length, vehicle);
    }

    public void addAll(List<? extends Vehicle> vehicleList) {
        vehicleList.forEach(vehicle -> add(vehicle));
    }

    public void add(int index, Vehicle vehicle) {
        if (index >= vehicles.length) {
            index = vehicles.length;
            addNewValue(index, vehicle);
        } else {
            if (vehicles[index] == null) {
                vehicles[index] = vehicle;
            } else {
                addNewValue(index, vehicle);
            }
        }
    }

    public void set(int index, Vehicle vehicle) {
        if (index < 0 ) {
            throw new IllegalArgumentException("Index out of range: " + index);
        }
        if (index >= vehicles.length) {
            index = vehicles.length;
        }
        setNewValue(index, vehicle);
    }

    public Vehicle get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index out of range: " + index);
        }
        return vehicles[index];
    }

    public int indexOf(Vehicle vehicle) {
        for (int i = 0; i < vehicles.length; i++) {
            if (vehicle.equals(vehicles[i])) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        vehicles = new Vehicle[10];
        size = 0;
        totalPrice = BigDecimal.ZERO;
    }

    public Vehicle remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index out of range: " + index);
        }

        final Vehicle vehicle = vehicles[index];
        if (index < vehicles.length - 1) {
            final int tail = vehicles.length - (index + 1);
            System.arraycopy(vehicles, index + 1, vehicles, index, tail);
            vehicles[vehicles.length - 1] = null;
        } else {
            vehicles[index] = null;
        }

        size--;
        totalPrice = totalPrice.subtract(vehicle.getPrice());
        return vehicle;
    }

    private void addNewValue(int index, Vehicle vehicle) {
        if (index < 0) {
            throw new IllegalArgumentException("Index out of range: " + index);
        }

        if (size + 1 >= vehicles.length) {
            increaseCapacity();
        }

        if (vehicles[index] != null) {
            final int tail = vehicles.length - index - 1;
            System.arraycopy(vehicles, index, vehicles, index + 1, tail);
        }
        vehicles[index] = vehicle;
        size++;
        totalPrice = totalPrice.add(vehicle.getPrice());
    }

    private void setNewValue(int index, Vehicle vehicle) {
        if (index < 0) {
            throw new IllegalArgumentException("Index out of range: " + index);
        }

        if (index >= vehicles.length) {
            increaseCapacity();
        }

        totalPrice = totalPrice.subtract(vehicles[index].getPrice());
        vehicles[index] = vehicle;
        totalPrice = totalPrice.add(vehicle.getPrice());
    }
    private void increaseCapacity() {
        final int length = vehicles.length;
        final int newLength = length * 3 / 2 + 1;
        final Vehicle[] newVehicles = new Vehicle[newLength];
        System.arraycopy(vehicles, 0, newVehicles, 0, length);
        vehicles = newVehicles;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Order ").append(id).append("\n");
        for (Vehicle vehicle : vehicles) {
            if (vehicle == null) {
                continue;
            }
            builder.append(String.format("Vehicle %s count %d price %.2f%n",
                    vehicle.getModel(), vehicle.getCount(), vehicle.getPrice()));
        }
        builder.append(String.format("Total price %.2f", totalPrice));
        return builder.toString();
    }
}
