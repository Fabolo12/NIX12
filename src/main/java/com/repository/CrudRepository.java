package com.repository;

import com.model.Auto;
import com.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<O extends Vehicle> {
    Optional<O> findById(String id);

    List<O> getAll();

    boolean save(O auto);

    boolean saveAll(List<O> auto);

    boolean update(O auto);

    boolean delete(String id);
}
