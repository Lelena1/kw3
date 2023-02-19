package me.luppolem.socksapp.services;

import me.luppolem.socksapp.model.Warehouse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface FileService {
    boolean saveToFile(String json);

    String readFromFile();

    boolean cleanDataFile();

    File getDataFile();

    InputStreamResource exportFile() throws FileNotFoundException;


    InputStreamResource exportTxtFile(Map<Integer, Warehouse> warehouseMap) throws FileNotFoundException, IOException;

    void importFile(MultipartFile file) throws FileNotFoundException;



    Path getPath();
}
