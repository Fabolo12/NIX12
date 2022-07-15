package com.service;

import com.model.Auto;
import com.model.Bus;
import com.model.Manufacturer;
import com.repository.AutoRepository;
import com.repository.BusRepository;
import com.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class BusService extends VehicleService<Bus> {

    public BusService(CrudRepository<Bus> repository) {
        super(repository);
    }

    @Override
    protected Bus creat() {
        return new Bus(
                "Model-" + RANDOM.nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                100,
                100
        );
    }
}
