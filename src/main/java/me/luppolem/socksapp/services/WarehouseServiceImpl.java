package me.luppolem.socksapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.luppolem.socksapp.exception.FileProcessingException;
import me.luppolem.socksapp.model.Warehouse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    private final FileService fileService;

    private Map<Integer, Warehouse> warehouseMap = new HashMap<>();
    private static Integer id = 0;

    public WarehouseServiceImpl(@Qualifier("warehouseFileService") FileService fileService) {
        this.fileService = fileService;
    }

    public Map<Integer, Warehouse> getWarehouseMap() {
        return warehouseMap;
    }

    @Override
    public Warehouse addWarehouse(Warehouse warehouse) {
        warehouseMap.put(id++, warehouse);
        saveToFileWarehouse();
        return warehouse;
    }

    @Override
    public Warehouse getWarehouse(Integer id) {
        if (!warehouseMap.containsKey(id)) {
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        return warehouseMap.get(id);
    }

    @Override
    public Collection<Warehouse> getAll() {
        return warehouseMap.values();
    }

    @Override
    public Warehouse removeWarehouse(int id) {
        if (!warehouseMap.containsKey(id)) {
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        Warehouse removedWarehouse = warehouseMap.remove(id);
        saveToFileWarehouse();
        return removedWarehouse;
    }

    @Override
    public Warehouse updateWarehouse(int id, Warehouse warehouse) {
        if (warehouseMap.containsKey(id)) {
            throw new NotFoundException("носки на складе с заданным id не найдены");
        }
        warehouseMap.put(id, warehouse);
        saveToFileWarehouse();
        return warehouse;
    }

    @PostConstruct
    private void initWarehouse() throws FileProcessingException {
        readFromFileWarehouse();
    }

    private void readFromFileWarehouse() throws FileProcessingException {
        try {
            String json = fileService.readFromFile();
            warehouseMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Warehouse>>() {
            });
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Файл не удалось прочитать");
        }
    }

    private void saveToFileWarehouse() throws FileProcessingException {
        try {
            String json = new ObjectMapper().writeValueAsString(warehouseMap);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Файл не удалось сохранить");
        }
    }

}
