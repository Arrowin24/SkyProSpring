package com.example.skyprospring.services;

import com.example.skyprospring.model.Ingredient;
import com.example.skyprospring.model.Recipe;
import org.springframework.stereotype.Service;

public interface RecipeService {
    public Recipe getRecipeById(long id);
    public void addRecipe(Recipe recipe);
}
