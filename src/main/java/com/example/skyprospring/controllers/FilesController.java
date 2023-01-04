package com.example.skyprospring.controllers;

import com.example.skyprospring.services.FilesService;
import com.example.skyprospring.services.RecipeService;
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

    public RecipeService recipeService;

    public FilesController(FilesService filesService, RecipeService recipeService) {
        this.filesService = filesService;
        this.recipeService = recipeService;
    }

    @GetMapping("/export/recipes")
    public ResponseEntity<InputStreamResource> downloadRecipeFile() {
        File downloadedFile = filesService.downloadRecipeFile();
        try {
            InputStreamResource stream = new InputStreamResource(new FileInputStream(downloadedFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(downloadedFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipes.json\"")
                    .body(stream);
        } catch (IOException e) {
            return ResponseEntity.noContent().build();
        }
    }


    @GetMapping("/export/recipes/txt")
    public ResponseEntity<InputStreamResource> downloadRecipeTxtFile() throws IOException {
        File downloadedFile = recipeService.createRecipesTxtFile();
        try {
            InputStreamResource stream = new InputStreamResource(new FileInputStream(downloadedFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(downloadedFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipes.txt\"")
                    .body(stream);
        } catch (IOException e) {
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
