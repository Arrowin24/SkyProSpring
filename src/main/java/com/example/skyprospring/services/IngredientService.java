package com.example.skyprospring.services;

import com.example.skyprospring.model.Ingredient;
import org.springframework.stereotype.Service;

public interface IngredientService {
    public Ingredient getIngredientById(long id);
    public void addIngredient(Ingredient ingredient);
}
