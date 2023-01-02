package com.example.skyprospring.services;

import java.io.File;

public interface FilesService {

    boolean saveIngredientToFile(String json);

    String readIngredientsFromFile();

    boolean saveRecipesToFile(String json);

    String readRecipesFromFile();

    void cleanIngredientFile();

    void cleanRecipeFile();

    File getRecipeDataFile();

    File getIngredientDataFile();
}
