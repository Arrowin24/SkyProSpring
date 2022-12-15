package com.example.skyprospring.services.imp;

import com.example.skyprospring.model.Ingredient;
import com.example.skyprospring.services.IngredientService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final Map<Long, Ingredient> ingredients = new TreeMap<>();
    private static long lastId = 0;

    @Override
    public Ingredient getIngredientById(long id) {
        return ingredients.get(id);
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        ingredients.put(lastId++, ingredient);
    }
}
