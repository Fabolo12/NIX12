package com.service;

import com.model.vehicle.Bus;
import com.model.vehicle.Manufacturer;
import com.repository.BusRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;

class VehicleServiceTest {

    private VehicleService<Bus> target;

    private BusRepository repository;

    private Bus auto;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(BusRepository.class);
        target = new VehicleService<>(repository) {
            @Override
            protected Bus creat() {
                return createSimpleAuto();
            }
        };
        auto = createSimpleAuto();
    }

    private Bus createSimpleAuto() {
        return new Bus("Model", Manufacturer.BMW,
                BigDecimal.valueOf(1000.0), 100, 100);
    }


    @Test
    void createAutos_negativeCount() {
        // configuration

        // call test method
        final List<Bus> actual = target.createAndSave(-1);

        // checks
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createAutos_zeroCount() {
        // configuration

        // call test method
        final List<Bus> actual = target.createAndSave(0);

        // checks
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createAutos() {
        // configuration

        // call test method
        final List<Bus> actual = target.createAndSave(5);

        // checks
        Assertions.assertEquals(5, actual.size());
        Mockito.verify(repository, Mockito.times(5)).save(Mockito.any(Bus.class));
    }

    @Test
    void save_one() {
        // configuration

        // call test method
        target.save(auto);

        // checks
        Mockito.verify(repository, Mockito.atLeastOnce()).save(auto);
    }

    @Test
    void save_many() {
        // configuration
        final List<Bus> buses = List.of(
                auto,
                auto,
                auto
        );

        // call test method
        target.save(buses);

        // checks
        Mockito.verify(repository).saveAll(buses);
    }

    @Test
    void update_success() {
        // configuration
        auto.setPrice(BigDecimal.TEN);
        Mockito.when(repository.findById(auto.getId()))
                .thenThrow(RuntimeException.class);

        // call test method
        target.update(auto);

        // checks
        Mockito.verify(repository).update(argThat(new ArgumentMatcher<>() {
            @Override
            public boolean matches(Bus actual) {
                return actual.getId().equals(auto.getId()) &&
                        actual.getPrice().equals(auto.getPrice());
            }
        }));
    }

    @Test
    void update_bodyTypeEmpty_hasOne() {
        // configuration
        final Bus otherAuto = createSimpleAuto();
        auto.setModel("");
        Mockito.when(repository.findById(auto.getId()))
                .thenReturn(Optional.of(otherAuto));
        Mockito.when(repository.update(otherAuto)).thenReturn(true);

        // call test method
        final boolean actual = target.update(auto);

        // checks
        Assertions.assertTrue(actual);
        Mockito.verify(repository).findById(auto.getId());
        Mockito.verify(repository).update(otherAuto);
    }
}