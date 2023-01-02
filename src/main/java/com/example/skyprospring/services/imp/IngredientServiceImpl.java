package com.example.skyprospring.services.imp;

import com.example.skyprospring.model.Ingredient;
import com.example.skyprospring.services.FilesService;
import com.example.skyprospring.services.IngredientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientServiceImpl implements IngredientService {
    final private FilesService filesService;

    private Map<Long, Ingredient> ingredients = new TreeMap<>();
    private static long lastId = 0;

    public IngredientServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Ingredient getIngredientById(long id) {
        if (ingredients.containsKey(id)) {
            return ingredients.get(id);
        }
        return null;
    }

    @Override
    public Map<Long, Ingredient> getAllIngredients() {
        if (ingredients.isEmpty()) {
            return null;
        }
        return ingredients;
    }

    @Override
    public long addIngredient(Ingredient ingredient) {
        ingredients.put(lastId, ingredient);
        saveToFile();
        return lastId++;
    }

    @Override
    public Ingredient editIngredient(long id, Ingredient newIngredient) {
        if (ingredients.containsKey(id)) {
            ingredients.put(id, newIngredient);
            saveToFile();
            return ingredients.get(id);
        }
        return null;
    }

    @Override
    public boolean removeIngredient(long id) {
        if (ingredients.containsKey(id)) {
            ingredients.remove(id);
            saveToFile();
            return true;
        }
        return false;
    }

    private void readFromFile() {
        try {
            String json = filesService.readIngredientsFromFile();
            if(!json.isBlank()){
                ingredients = new ObjectMapper().readValue(json, new TypeReference<>() {
                });
                lastId = ingredients.size();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            filesService.saveIngredientToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
