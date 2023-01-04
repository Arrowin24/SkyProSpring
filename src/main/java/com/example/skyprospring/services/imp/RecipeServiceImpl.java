package com.example.skyprospring.services.imp;

import com.example.skyprospring.model.Ingredient;
import com.example.skyprospring.model.Recipe;
import com.example.skyprospring.services.FilesService;
import com.example.skyprospring.services.IngredientService;
import com.example.skyprospring.services.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final IngredientService ingredientService;
    private final FilesService filesService;

    public RecipeServiceImpl(IngredientService ingredientService, FilesService filesService) {
        this.ingredientService = ingredientService;
        this.filesService = filesService;
    }

    private Map<Long, Recipe> recipes = new TreeMap<>();
    private static long lastId = 0;

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Recipe getRecipeById(long id) {
        if (recipes.containsKey(id)) {
            return recipes.get(id);
        }
        return null;
    }

    @Override
    public Map<Long, Recipe> getAllRecipes() {
        if (recipes.isEmpty()) {
            return null;
        }
        return recipes;
    }

    @Override
    public long addRecipe(Recipe recipe) {
        recipes.put(lastId, recipe);
        saveToFile();
        return lastId++;
    }

    @Override
    public Recipe editRecipe(long id, Recipe newRecipe) {
        if (recipes.containsKey(id)) {
            recipes.put(id, newRecipe);
            saveToFile();
            return recipes.get(id);
        }
        return null;
    }

    @Override
    public boolean removeRecipe(long id) {
        if (recipes.containsKey(id)) {
            recipes.remove(id);
            saveToFile();
            return true;
        }
        return false;
    }

    @Override
    public List<Recipe> getRecipesByIngredientsId(List<Long> ids) {
        List<Ingredient> ingredientList = getIngredientListById(ids);
        List<Recipe> recipeList = new ArrayList<>();
        if (ingredientList.isEmpty()) {
            return null;
        }
        for (long i = 0; i < recipes.size(); i++) {
            boolean isContainsAll = true;
            for (Ingredient ingredient : ingredientList) {
                if (!recipes.get(i).getIngredients().contains(ingredient)) {
                    isContainsAll = false;
                    break;
                }
            }
            if (isContainsAll) {
                recipeList.add(recipes.get(i));
            }
        }
        return recipeList;
    }

    private List<Ingredient> getIngredientListById(List<Long> ids) {
        List<Ingredient> ingredientList = new ArrayList<>();
        for (Long id : ids) {
            if (ingredientService.getIngredientById(id) == null) {
                continue;
            }
            ingredientList.add(ingredientService.getIngredientById(id));
        }
        return ingredientList;
    }

    @Override
    public List<Recipe> getTensRecipeByPage(long page) {
        List<Recipe> tenRecipes = new ArrayList<>();
        for (long i = page * 10; i < (page + 1) * 10; i++) {
            if (recipes.containsKey(i)) {
                tenRecipes.add(recipes.get(i));
            }
        }
        return tenRecipes;
    }

    @Override
    public File createRecipesTxtFile() {
        Path path = filesService.createTempFile("Recipes");
        try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            for (Recipe recipe : recipes.values()) {
                writer.append(recipe.toString());
                writer.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path.toFile();
    }

    private void readFromFile() {
        try {
            String json = filesService.readRecipesFromFile();
            if (!json.isBlank()) {
                recipes = new ObjectMapper().readValue(json, new TypeReference<>() {
                });
                lastId = recipes.size();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            filesService.saveRecipesToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
