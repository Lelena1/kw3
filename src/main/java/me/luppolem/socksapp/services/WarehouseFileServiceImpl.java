package me.luppolem.socksapp.services;

import me.luppolem.socksapp.exception.FileProcessingException;
import me.luppolem.socksapp.model.Warehouse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

@Service("warehouseFileService")
public class WarehouseFileServiceImpl implements FileService {


    @Value("${path.to.files}")
    private String dataFilePathWarehouse;
    @Value("${name.of.warehouse.file}")
    private String dataFileNameWarehouse;

    private Path path;

    @PostConstruct
    private void init() {
        path = Path.of(dataFilePathWarehouse, dataFileNameWarehouse);
    }

    @Override
    public boolean saveToFile(String json) {
        try {
            cleanDataFile();
            Files.writeString(path, json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String readFromFile() {
        if (Files.exists(path)) {
            try {
                return Files.readString(path);
            } catch (IOException e) {
                throw new FileProcessingException("Не удалось прочитать файл");
            }
        } else {
            return "{}";
        }
    }

    @Override
    public boolean cleanDataFile() {
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getDataFile() {
        return new File(dataFilePathWarehouse + "/" + dataFileNameWarehouse);
    }


    @Override
    public InputStreamResource exportFile() throws FileNotFoundException {
        File file = getDataFile();
        return new InputStreamResource(new FileInputStream(file));
    }

    @Override
    public InputStreamResource exportTxtFile(Map<Integer, Warehouse> recipeMap) throws FileNotFoundException, IOException {
        Path path = this.createAllWarehouseFile("allWarehouse");
        for (Warehouse warehouse : warehouseMap.values()) {
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append(" Цвет носков: ");
                writer.append(warehouse.getColor());
                writer.append("\n Размер носков: ");
                writer.append(String.valueOf(warehouse.getSize()));
                writer.append(" ");
                writer.append("\n Процентное содержание хлопка: ");
                writer.append(String.valueOf(warehouse.getCottonPart()));
                writer.append("\n Количество носков: ");
                writer.append(String.valueOf(warehouse.getQuantity()));
            }
        }

        File file = path.toFile();
        return new InputStreamResource(new FileInputStream(file));
    }


    private Path createAllWarehouseFile(String suffix) throws IOException {
        if (Files.exists(Path.of(dataFilePathWarehouse, suffix))) {
            Files.delete(Path.of(dataFilePathWarehouse, suffix));
            Files.createFile(Path.of(dataFilePathWarehouse, suffix));
            return Path.of(dataFilePathWarehouse, suffix);
        }
        return Files.createFile(Path.of(dataFilePathWarehouse, suffix));
    }

    @Override
    public void importFile(MultipartFile file) throws FileNotFoundException {
        cleanDataFile();
        FileOutputStream fos = new FileOutputStream(getDataFile());
        try {
            IOUtils.copy(file.getInputStream(), fos);
        } catch (IOException e) {
            throw new FileProcessingException("Проблема сохранения файла");
        }
    }

    @Override
    public Path getPath() {
        return path;
    }
}
