package me.luppolem.socksapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.luppolem.socksapp.exception.FileProcessingException;
import me.luppolem.socksapp.exception.SocksExistsException;
import me.luppolem.socksapp.model.Socks;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class SocksServiceImpl implements SocksService {


    private final FileService fileService;

    public SocksServiceImpl(@Qualifier("socksFileService") FileService fileService) {
        this.fileService = fileService;
    }

    private Map<Integer, Socks> socksMap = new HashMap<>();
    private static Integer id = 0;

    @Override
    public Socks addSocks(Socks socks) {
        if (socksMap.containsValue(socks)) {
            throw new SocksExistsException();
        }
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
    public Collection<Socks> getAll() {
        return socksMap.values();
    }

    @Override
    public Socks removeSocks(int id) {
        if (!socksMap.containsKey(id)) {
            throw new NotFoundException("Ингредиент с заданным id Не найден");
        }
        Socks removedSocks = socksMap.remove(id);
        saveToFileSocks();
        return removedSocks;
    }


    @Override
    public Socks updateSocks(int id, Socks socks) {
        if (!socksMap.containsKey(id)) {
            throw new NotFoundException("Носки с заданным id не найдены");
        }
        socksMap.put(id, socks);
        saveToFileSocks();
        return socks;
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
