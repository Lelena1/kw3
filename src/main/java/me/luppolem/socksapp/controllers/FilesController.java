package me.luppolem.socksapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.luppolem.socksapp.services.FileService;
import me.luppolem.socksapp.services.SocksService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/files")
@Tag(name = "Files", description = "CRUD-операции для работы с файлами")
public class FilesController {


    private final FileService fileService;
    private final SocksService socksService;

    public FilesController(
            FileService fileService, SocksService socksService) {
        this.fileService = fileService;
        this.socksService = socksService;

    }


    @PostMapping(value = "/socks/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Импорт файла носков")
    public ResponseEntity<Void> uploadDataFileSocks(@RequestParam MultipartFile file) throws FileNotFoundException {
        fileService.importFile(file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/socks/exporttxt")
    @Operation(description = "Экспорт файла носков")
    public ResponseEntity<InputStreamResource> downloadSocksFile() throws IOException {
        InputStreamResource inputStreamResource = fileService.exportTxtFile(socksService.getSocksMap());
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(Files.size(fileService.getPath()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\"socks.txt\"")
                .body(inputStreamResource);
    }

    @GetMapping("/socks/export")
    @Operation(description = "Экспорт файла носков")
    public ResponseEntity<InputStreamResource> downloadTxtSocksFile() throws IOException {
        InputStreamResource inputStreamResource = fileService.exportFile();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(Files.size(fileService.getPath()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\"socks.json\"")
                .body(inputStreamResource);
    }
}
