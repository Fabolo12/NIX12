package com.repository;

import com.model.Auto;

import java.util.List;

public interface CrudRepository {
    Auto getById(String id);

    List<Auto> getAll();

    boolean save(Auto auto);

    boolean saveAll(List<Auto> auto);

    boolean update(Auto auto);

    boolean delete(String id);
}
