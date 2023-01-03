package com.example.skyprospring.controllers;

import com.example.skyprospring.services.FilesService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FilesController {
    public FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @GetMapping("/export/recipes")
    public ResponseEntity<InputStreamResource> downloadRecipeFile() throws IOException {
        InputStreamResource downloadedFile = filesService.downloadRecipeFile();
        if (downloadedFile != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(downloadedFile.contentLength())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipes.json\"")
                    .body(downloadedFile);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/import/recipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipesFile(@RequestParam MultipartFile file) {
        if (filesService.uploadRecipeFile(file)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/import/ingredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadIngredientsFile(@RequestParam MultipartFile file) {
        if (filesService.uploadIngredientFile(file)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
