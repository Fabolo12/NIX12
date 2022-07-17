package com.service;

import com.model.vehicle.Bus;
import com.repository.CrudRepository;

import java.math.BigDecimal;

public class BusService extends VehicleService<Bus> {

    public BusService(CrudRepository<Bus> repository) {
        super(repository);
    }

    @Override
    protected Bus creat() {
        return new Bus(
                Bus.class.getSimpleName() + RANDOM.nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                100,
                100
        );
    }
}
