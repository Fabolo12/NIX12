package com.service;

import com.model.vehicle.Auto;
import com.model.vehicle.Manufacturer;
import com.repository.AutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;

class AutoServiceTest {

    private AutoService target;
    private AutoRepository autoRepository;

    @BeforeEach
    void setUp() {
        autoRepository = Mockito.mock(AutoRepository.class);
        target = new AutoService(autoRepository);
    }

    @Test
    void printAll() {
        List<Auto> autos = List.of(createSimpleAuto(), createSimpleAuto());
        Mockito.when(autoRepository.getAll()).thenReturn(autos);
        target.printAll();
    }

    private Auto createSimpleAuto() {
        return new Auto("Model", Manufacturer.BMW, BigDecimal.ZERO, "Type", 1);
    }

    @Test
    void findOneById_null1() {
        final Auto expected = createSimpleAuto();
        Mockito.when(autoRepository.findById("")).thenReturn(Optional.of(expected));
        final Optional<Auto> actual = target.findOneById(null);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(expected.getId(), actual.get().getId());
    }

    @Test
    void findOneById_null2() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        target.findOneById(null);
        Mockito.verify(autoRepository).findById(captor.capture());
        Assertions.assertEquals("", captor.getValue());
    }

    @Test
    void update_bodyTypeEmpty_hasntOne() {
        final Auto simpleAuto = createSimpleAuto();
        simpleAuto.setBodyType("");
        Mockito.when(autoRepository.findById(simpleAuto.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> target.update(simpleAuto));
    }

    @Test
    void update_zeroPrice() {
        final Auto simpleAuto = createSimpleAuto();
        target.update(simpleAuto);
        Mockito.verify(autoRepository).update(argThat(new ArgumentMatcher<>() {
            @Override
            public boolean matches(Auto auto) {
                return simpleAuto.getId().equals(auto.getId()) &&
                        BigDecimal.valueOf(-1).equals(auto.getPrice());
            }
        }));
    }

    @Test
    void update_manufacturerNull() {
        final Auto simpleAuto = createSimpleAuto();
        simpleAuto.setManufacturer(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.update(simpleAuto));
    }

}