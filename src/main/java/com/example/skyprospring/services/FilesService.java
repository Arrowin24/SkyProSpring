package com.example.skyprospring.services;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;

public interface FilesService {

    boolean saveIngredientToFile(String json);

    String readIngredientsFromFile();

    boolean saveRecipesToFile(String json);

    String readRecipesFromFile();

    void cleanIngredientFile();

    void cleanRecipeFile();

    File getRecipeDataFile();

    File getIngredientDataFile();

    InputStreamResource downloadRecipeFile() throws FileNotFoundException;

    boolean uploadRecipeFile(MultipartFile file);

    boolean uploadIngredientFile(MultipartFile file);
}
