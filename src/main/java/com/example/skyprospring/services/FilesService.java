package com.example.skyprospring.services;

public interface FilesService {

    boolean saveIngredientToFile(String json);

    String readIngredientsFromFile();

    boolean saveRecipesToFile(String json);

    String readRecipesFromFile();
}
