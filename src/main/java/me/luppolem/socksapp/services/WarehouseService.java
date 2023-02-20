package me.luppolem.socksapp.services;

import me.luppolem.socksapp.model.Warehouse;

import java.util.Collection;
import java.util.Map;

public interface WarehouseService {


    Map<Integer, Warehouse> getWarehouseMap(Integer id);

    Warehouse addWarehouse(Warehouse warehouse);

    Warehouse getWarehouse(Integer id);

    Collection<Warehouse> getAll();

    Warehouse removeWarehouse(int id);

    Warehouse updateWarehouse(int id, Warehouse warehouse);
}
