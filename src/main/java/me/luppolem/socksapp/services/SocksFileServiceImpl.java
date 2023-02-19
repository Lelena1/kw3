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
import java.util.Map;

@Service("socksFileService")
public class SocksFileServiceImpl implements FileService {


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
                throw new FileProcessingException("не удалось прочитать файл");
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
    public InputStreamResource exportTxtFile(Map<Integer, Warehouse> recipeMap) throws FileNotFoundException, IOException {
        return null;
    }

    @Override
    public void importFile(MultipartFile file) throws FileNotFoundException {
        cleanDataFile();
        FileOutputStream fos = new FileOutputStream(getDataFile());
        try {
            IOUtils.copy(file.getInputStream(), fos);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileProcessingException("Проблема сохранения файла");
        }
    }

    @Override
    public Path getPath() {
        return path;
    }
}
