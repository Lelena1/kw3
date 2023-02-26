package me.luppolem.socksapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.luppolem.socksapp.exception.FileProcessingException;
import me.luppolem.socksapp.model.Color;
import me.luppolem.socksapp.model.Size;
import me.luppolem.socksapp.model.Socks;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SocksServiceImpl implements SocksService {


    private final FileService fileService;
    private static Map<Integer, Socks> socksMap = new HashMap<>();
    private static Integer id = 0;


    @Override
    public Socks addSocks(Socks socks) {


        socksMap.put(id++, socks);
        saveToFileSocks();
        return socks;
    }

    @Override
    public Socks getSocks(Integer id) {
        if (!socksMap.containsKey(id)) {
            throw new NotFoundException("Носки с заданным id не найдены");
        }
        return socksMap.get(id);
    }

    @Override
    public Collection<Socks> getAllSocks() {
        return socksMap.values();
    }

    @Override
    public Map<Integer, Socks> getSocksMap() {
        return socksMap;
    }


    @Override
    public void removeDefectiveSocks(Color color, Size size, int cottonPart, int quantity) {

        for (Map.Entry<Integer, Socks> entry : socksMap.entrySet()) {
            if (entry.getValue().getColor().equals(color) &&
                    entry.getValue().getSize().equals(size) &&
                    entry.getValue().getCottonPart() == cottonPart &&
                    entry.getValue().getQuantity() >= quantity) {
                socksMap.put(id, new Socks(color, size, cottonPart,
                        entry.getValue().getQuantity() - quantity));
                saveToFileSocks();

            }
            throw new NotFoundException("Бракованные носки с заданными атрибутами не найдены на складе");
        }

    }


    @Override
    public boolean updateSocks(Color color, Size size, int cottonPart, int quantity) {


        for (Map.Entry<Integer, Socks> entry : socksMap.entrySet()) {
            if (entry.getValue().getColor().equals(color) &&
                    entry.getValue().getSize().equals(size) &&
                    entry.getValue().getCottonPart() == cottonPart &&
                    entry.getValue().getQuantity() >= quantity) {
                socksMap.put(id, new Socks(color, size, cottonPart,
                        entry.getValue().getQuantity() - quantity));
                saveToFileSocks();
                return true;
            }
            throw new NotFoundException("Носки с заданными атрибутами не удалось списать со склада");

        }

        return false;
    }


    @Override
    public Integer getQuantityOfSocks(Color color, Size size, Integer cottonMin, Integer cottonMax) {
        int quantityOfSocks = 0;
        for (Map.Entry<Integer, Socks> entry : socksMap.entrySet()) {
            if (entry.getValue().getColor().equals(color) &&
                    entry.getValue().getSize().equals(size) &&
                    (cottonMin <= entry.getValue().getCottonPart() &&
                            (cottonMax >= entry.getValue().getCottonPart()))) {
                quantityOfSocks += entry.getValue().getQuantity();
            }
        }
        return quantityOfSocks;
    }


    @PostConstruct
    private void initSocks() throws FileProcessingException {
        readFromFileSocks();
    }

    private void readFromFileSocks() throws FileProcessingException {
        try {
            String json = fileService.readFromFile();
            socksMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Socks>>() {
            });
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Файл не удалось прочитать");
        }
    }

    private void saveToFileSocks() throws FileProcessingException {
        try {
            String json = new ObjectMapper().writeValueAsString(socksMap);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Файл не удалось сохранить");
        }
    }
}
