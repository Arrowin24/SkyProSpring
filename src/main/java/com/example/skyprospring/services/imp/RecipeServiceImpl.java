package com.example.skyprospring.services.imp;

import com.example.skyprospring.model.Ingredient;
import com.example.skyprospring.model.Recipe;
import com.example.skyprospring.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final Map<Long, Recipe> recipes = new TreeMap<>();
    private static long lastId = 0;

    @Override
    public Recipe getRecipeById(long id) {
        return recipes.get(id);
    }

    @Override
    public void addRecipe(Recipe recipe) {
        recipes.put(lastId++, recipe);
    }
}
