package com.service;

import com.model.vehicle.Manufacturer;
import com.model.vehicle.Vehicle;
import com.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * @param <T>
 */
public abstract class VehicleService<T extends Vehicle> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleService.class);
    protected static final Random RANDOM = new Random();
    protected final CrudRepository<T> repository;

    protected VehicleService(CrudRepository<T> repository) {
        this.repository = repository;
    }

    public List<T> createAndSave(int count) {
        List<T> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final T vehicle = creat();
            result.add(vehicle);
            repository.save(vehicle);
            LOGGER.debug("Created vehicle {}", vehicle.getId());
        }
        return result;
    }

    protected abstract T creat();

    protected Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void save(T vehicle) {
        repository.save(vehicle);
    }


    public boolean update(T vehicle) {
        if (vehicle.getPrice().equals(BigDecimal.ZERO)) {
            vehicle.setPrice(BigDecimal.valueOf(-1));
        }
        if (vehicle.getModel().equals("")) {
            vehicle = repository.findById(vehicle.getId())
                    .orElseThrow(IllegalArgumentException::new);
        }
        if (vehicle.getManufacturer() == null) {
            throw new IllegalArgumentException();
        }
        return repository.update(vehicle);
    }

    public void save(List<T> autos) {
        repository.saveAll(autos);
    }

    public void printAll() {
        for (T vehicle : repository.getAll()) {
            System.out.println(vehicle);
        }
    }

    public Optional<T> findOneById(String id) {
        return id == null ? repository.findById("") : repository.findById(id);
    }
}
