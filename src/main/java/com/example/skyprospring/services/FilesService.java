package com.example.skyprospring.services;

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

    File downloadRecipeFile();

    boolean uploadRecipeFile(MultipartFile file);

    boolean uploadIngredientFile(MultipartFile file);
}
