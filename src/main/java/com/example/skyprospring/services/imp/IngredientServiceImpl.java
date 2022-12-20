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
        if (ingredients.containsKey(id)) {
            return ingredients.get(id);
        }
        return null;
    }

    @Override
    public Map<Long, Ingredient> getAllIngredients() {
        if(ingredients.isEmpty()){
            return null;
        }
        return ingredients;
    }

    @Override
    public long addIngredient(Ingredient ingredient) {
        ingredients.put(lastId, ingredient);
        return lastId++;
    }

    @Override
    public Ingredient editIngredient(long id, Ingredient newIngredient) {
        if (ingredients.containsKey(id)) {
            ingredients.put(id, newIngredient);
            return ingredients.get(id);
        }
        return null;
    }

    @Override
    public boolean removeIngredient(long id) {
        if (ingredients.containsKey(id)) {
            ingredients.remove(id);
            return true;
        }
        return false;
    }
}
