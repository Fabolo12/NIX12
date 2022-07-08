package com.service;

import com.model.Auto;
import com.model.Manufacturer;
import com.repository.AutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

class AutoServiceTest {

    private AutoService target;
    private AutoRepository autoRepository;

    @BeforeEach
    void setUp() {
        autoRepository = Mockito.mock(AutoRepository.class);
        target = new AutoService(autoRepository);
    }

    @Test
    void createAutos_negativeCount() {
        final List<Auto> actual = target.createAndSaveAutos(-1);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createAutos_zeroCount() {
        final List<Auto> actual = target.createAndSaveAutos(0);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createAutos() {
        final List<Auto> actual = target.createAndSaveAutos(5);
        Assertions.assertEquals(5, actual.size());
        Mockito.verify(autoRepository, Mockito.times(5))
                .save(Mockito.any());
    }

    @Test
    void saveAutos() {
    }

    @Test
    void printAll() {
        List<Auto> autos = List.of(createSimpleAuto(), createSimpleAuto());
        Mockito.when(autoRepository.getAll()).thenReturn(autos);
        target.printAll();
    }

    private Auto createSimpleAuto() {
        return new Auto("Model", Manufacturer.BMW, BigDecimal.ZERO, "Type");
    }

    @Test
    void findOneById_null1() {
        final Auto expected = createSimpleAuto();
        Mockito.when(autoRepository.getById("")).thenReturn(expected);
        final Auto actual = target.findOneById(null);
        Assertions.assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void findOneById_null2() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        target.findOneById(null);
        Mockito.verify(autoRepository).getById(captor.capture());
        Assertions.assertEquals("", captor.getValue());
    }


}