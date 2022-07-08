package com.repository;

import com.model.Auto;
import com.model.Manufacturer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class AutoRepositoryTest {

    private AutoRepository target;

    private Auto auto;

    @BeforeEach
    void setUp() {
        target = new AutoRepository();
        auto = createSimpleAuto();
        target.save(auto);
    }

    private Auto createSimpleAuto() {
        return new Auto("Model", Manufacturer.BMW, BigDecimal.ZERO, "Type");
    }

    @Test
    void getById_findOne() {
        final Auto actual = target.getById(auto.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(auto.getId(), actual.getId());
    }

    @Test
    void getById_notFind() {
        final Auto actual = target.getById("1232");
        Assertions.assertNull(actual);
    }

    @Test
    void getById_findOne_manyAutos() {
        final Auto otherAuto = createSimpleAuto();
        target.save(otherAuto);
        final Auto actual = target.getById(auto.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(auto.getId(), actual.getId());
        Assertions.assertNotEquals(otherAuto.getId(), actual.getId());
    }

    @Test
    void getAll() {
        final List<Auto> actual = target.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void save_success_notChangePrice() {
        auto.setPrice(BigDecimal.ONE);
        final boolean actual = target.save(auto);
        Assertions.assertTrue(actual);
        final Auto actualAuto = target.getById(auto.getId());
        Assertions.assertEquals(BigDecimal.ONE, actualAuto.getPrice());
    }

    @Test
    void save_fail() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
    }

    @Test
    void save_success_changePrice() {
        target.save(auto);
        final Auto actual = target.getById(auto.getId());
        Assertions.assertEquals(BigDecimal.valueOf(-1), actual.getPrice());
    }

    @Test
    void saveAll_null() {
        final boolean actual = target.saveAll(null);
        Assertions.assertFalse(actual);
    }

    @Test
    void saveAll_emptyList() {
        final boolean actual = target.saveAll(Collections.emptyList());
        Assertions.assertFalse(actual);
    }

    @Test
    void saveAll() {
        final boolean actual = target.saveAll(List.of(createSimpleAuto()));
        Assertions.assertTrue(actual);
    }

    @Test
    void update_notFound() {
        final Auto otherAuto = createSimpleAuto();
        final boolean actual = target.update(otherAuto);
        Assertions.assertFalse(actual);
    }

    @Test
    void update() {
        auto.setPrice(BigDecimal.TEN);
        final boolean actual = target.update(auto);
        Assertions.assertTrue(actual);
        final Auto actualAuto = target.getById(auto.getId());
        Assertions.assertEquals(BigDecimal.TEN, actualAuto.getPrice());
    }

    @Test
    void updateByBodyType() {
        final Auto otherAuto = createSimpleAuto();
        otherAuto.setManufacturer(Manufacturer.KIA);
        otherAuto.setPrice(BigDecimal.TEN);

        final boolean actual = target.updateByBodyType(auto.getBodyType(), otherAuto);
        Assertions.assertTrue(actual);
        final Auto actualAuto = target.getById(auto.getId());
        Assertions.assertEquals(Manufacturer.BMW, actualAuto.getManufacturer());
        Assertions.assertEquals(BigDecimal.TEN, actualAuto.getPrice());
    }

    @Test
    void delete() {
    }
}