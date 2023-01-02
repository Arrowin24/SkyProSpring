package com.example.skyprospring.services;

import com.example.skyprospring.model.Ingredient;

import java.util.Map;

public interface IngredientService {
    Ingredient getIngredientById(long id);

    Map<Long,Ingredient> getAllIngredients();

    long addIngredient(Ingredient ingredient);


    Ingredient editIngredient(long id, Ingredient newIngredient);

    boolean removeIngredient(long id);
}
