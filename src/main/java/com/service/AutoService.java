package com.service;

import com.model.vehicle.Auto;
import com.model.vehicle.Manufacturer;
import com.repository.AutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

public class AutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoService.class);
    private static final Random RANDOM = new Random();
    private final AutoRepository autoRepository;

    public AutoService(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    public List<Auto> createAndSaveAutos(int count) {
        List<Auto> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Auto auto = new Auto(
                    Auto.class.getSimpleName() + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                    "Model-" + RANDOM.nextInt(1000),
                    100
            );
            result.add(auto);
            autoRepository.save(auto);
            LOGGER.debug("Created auto {}", auto.getId());
        }
        return result;
    }

    public void save(Auto auto) {
        autoRepository.save(auto);
    }

    public void getTotalSumOf(String id) {
        autoRepository.findById(id).ifPresent(auto -> {
            final int count = auto.getCount();
            final BigDecimal price = auto.getPrice();
            final int totalSum = count * price.intValue();
            System.out.printf("Auto %s has total sum %d%n", auto.getModel(), totalSum);
        });
    }

    public Auto getOrCreat(String id) {
        final Auto auto = autoRepository.findById(id).orElse(cretaOne());
        autoRepository.save(auto);
        return auto;
    }

    private Auto cretaOne() {
        return new Auto("Model", Manufacturer.KIA, BigDecimal.valueOf(1000.0), "Model", 100);
    }

    public Auto getOrCreatWithTotalSum(String id) {
        final Auto auto = autoRepository.findById(id).orElseGet(() -> {
            final List<Auto> all = autoRepository.getAll();
            BigDecimal totalSum = BigDecimal.ZERO;
            for (Auto a : all) {
                totalSum = totalSum.add(a.getPrice());
            }
            final Auto cretaOne = cretaOne();
            cretaOne.setPrice(totalSum);
            return cretaOne;
        });
        autoRepository.save(auto);
        return auto;
    }

    public boolean update(Auto auto) {
        if (auto.getPrice().equals(BigDecimal.ZERO)) {
            auto.setPrice(BigDecimal.valueOf(-1));
        }
        if (auto.getManufacturer() == null) {
            throw new IllegalArgumentException();
        }
        if (auto.getBodyType().equals("")) {
            auto = autoRepository.findById(auto.getId()).orElseThrow(IllegalArgumentException::new);
        }
        return autoRepository.update(auto);
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void saveAutos(List<Auto> autos) {
        autoRepository.saveAll(autos);
    }

    public void printAll() {
        for (Auto auto : autoRepository.getAll()) {
            System.out.println(auto);
        }
    }

    public Optional<Auto> findOneById(String id) {
        return id == null ? autoRepository.findById("") : autoRepository.findById(id);
    }
}
