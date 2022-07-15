package com;

import com.model.Bus;
import com.repository.AutoRepository;
import com.repository.BusRepository;
import com.service.AutoService;
import com.service.BusService;

import java.util.List;

public class Main {
    private static final AutoService AUTO_SERVICE =
            new AutoService(new AutoRepository());
    private static final BusService BUS_SERVICE =
            new BusService(new BusRepository());

    public static void main(String[] args) {
        /*final List<Auto> autos = AUTO_SERVICE.createAndSaveAutos(1);
        AUTO_SERVICE.getTotalSumOf(autos.get(0).getId());*/
        final List<Bus> buses = BUS_SERVICE.createAndSave(5);
        BUS_SERVICE.printAll();
    }
}

