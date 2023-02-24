package me.luppolem.socksapp.services;

import me.luppolem.socksapp.exception.FileProcessingException;
import me.luppolem.socksapp.model.Socks;
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
    private String dataFilePathSocks;
    @Value("${name.of.socks.file}")
    private String dataFileNameSocks;

    private Path path;

    @PostConstruct
    private void init() {
        path = Path.of(dataFilePathSocks, dataFileNameSocks);
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
        return new File(dataFilePathSocks + "/" + dataFileNameSocks);
    }


    @Override
    public InputStreamResource exportFile() throws FileNotFoundException {
        File file = getDataFile();
        return new InputStreamResource(new FileInputStream(file));
    }

    @Override
    public InputStreamResource exportTxtFile(Map<Integer, Socks> socksMap) throws FileNotFoundException, IOException {
        Path path = this.createAllSocksFile("allSocks");
        for (Socks socks : socksMap.values()) {
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append(" Цвет носков: ");
                writer.append(String.valueOf(socks.getColor()));
                writer.append("\n Размер носков: ");
                writer.append(String.valueOf(socks.getSize()));
                writer.append(" ");
                writer.append("\n Процентное содержание хлопка: ");
                writer.append(String.valueOf(socks.getCottonPart()));
                writer.append("\n Количество носков: ");
                writer.append(String.valueOf(socks.getQuantity()));
            }
        }

        File file = path.toFile();
        return new InputStreamResource(new FileInputStream(file));
    }


    private Path createAllSocksFile(String suffix) throws IOException {
        if (Files.exists(Path.of(dataFilePathSocks, suffix))) {
            Files.delete(Path.of(dataFilePathSocks, suffix));
            Files.createFile(Path.of(dataFilePathSocks, suffix));
            return Path.of(dataFilePathSocks, suffix);
        }
        return Files.createFile(Path.of(dataFilePathSocks, suffix));
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
