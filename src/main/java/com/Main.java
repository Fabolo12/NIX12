package com;

import com.model.Auto;
import com.repository.AutoRepository;
import com.service.AutoService;

import java.util.List;

public class Main {
    private static final AutoService AUTO_SERVICE = new AutoService(new AutoRepository());

    public static void main(String[] args) {
        final List<Auto> autos = AUTO_SERVICE.createAndSaveAutos(10);
        AUTO_SERVICE.saveAutos(autos);
        AUTO_SERVICE.printAll();
    }
}