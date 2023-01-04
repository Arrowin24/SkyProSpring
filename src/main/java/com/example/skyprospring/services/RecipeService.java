package com.example.skyprospring.services;


import com.example.skyprospring.model.Recipe;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public interface RecipeService {
    Recipe getRecipeById(long id);

    Map<Long, Recipe> getAllRecipes();

    long addRecipe(Recipe recipe);

    Recipe editRecipe(long id, Recipe newRecipe);

    boolean removeRecipe(long id);

    List<Recipe> getRecipesByIngredientsId(List<Long> ids);

    List<Recipe> getTensRecipeByPage(long page);

    File createRecipesTxtFile() throws FileNotFoundException;
}
