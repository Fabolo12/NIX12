package com.repository;

import com.model.Auto;
import com.model.Bus;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BusRepository implements CrudRepository<Bus> {
    private final List<Bus> autos;

    public BusRepository() {
        autos = new LinkedList<>();
    }

    @Override
    public Optional<Bus> findById(String id) {
        for (Bus auto : autos) {
            if (auto.getId().equals(id)) {
                return Optional.of(auto);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Bus> getAll() {
        return autos;
    }

    @Override
    public boolean save(Bus auto) {
        if (auto == null) {
            throw new IllegalArgumentException("Auto must not be null");
        }
        if (auto.getPrice().equals(BigDecimal.ZERO)) {
            auto.setPrice(BigDecimal.valueOf(-1));
        }
        autos.add(auto);
        return true;
    }

    @Override
    public boolean saveAll(List<Bus> auto) {
        if (auto == null) {
            return false;
        }
        return autos.addAll(auto);
    }

    @Override
    public boolean update(Bus auto) {
        final Optional<Bus> optionalAuto = findById(auto.getId());
        if (optionalAuto.isPresent()) {
            optionalAuto.ifPresent(founded -> BusCopy.copy(auto, founded));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Bus> iterator = autos.iterator();
        while (iterator.hasNext()) {
            final Bus auto = iterator.next();
            if (auto.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private static class BusCopy {
        static void copy(final Bus from, final Bus to) {
            to.setModel(from.getModel());
            to.setCountPeople(from.getCountPeople());
            to.setPrice(from.getPrice());
        }
    }
}
