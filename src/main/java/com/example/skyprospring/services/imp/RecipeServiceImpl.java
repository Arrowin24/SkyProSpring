package com.example.skyprospring.services.imp;

import com.example.skyprospring.model.Ingredient;
import com.example.skyprospring.model.Recipe;
import com.example.skyprospring.services.IngredientService;
import com.example.skyprospring.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final IngredientService ingredientService;

    public RecipeServiceImpl(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    private final Map<Long, Recipe> recipes = new TreeMap<>();
    private static long lastId = 0;

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
        return lastId++;
    }

    @Override
    public Recipe editRecipe(long id, Recipe newRecipe) {
        if (recipes.containsKey(id)) {
            recipes.put(id, newRecipe);
            return recipes.get(id);
        }
        return null;
    }

    @Override
    public boolean removeRecipe(long id) {
        if (recipes.containsKey(id)) {
            recipes.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Recipe> getRecipesByIngredientsId(List<Long> ids) {
        List<Ingredient> ingredientList = getIngredientListById(ids);
        List<Recipe> recipeList = new ArrayList<>();
        if (ingredientList == null) {
            return null;
        }
        for (long i = 0; i < recipes.size(); i++) {
            boolean isContainsAll = true;
            for (Ingredient ingredient : ingredientList) {
                if(!recipes.get(i).getIngredients().contains(ingredient)){
                    isContainsAll = false;
                }
            }
            if(isContainsAll){
                recipeList.add(recipes.get(i));
            }
        }
        return recipeList;
    }

    private List<Ingredient> getIngredientListById(List<Long> ids) {
        List<Ingredient> ingredientList = new ArrayList<>();
        for (Long id : ids) {
            if (ingredientService.getIngredientById(id) == null) {
                return null;
            }
            ingredientList.add(ingredientService.getIngredientById(id));
        }
        return ingredientList;
    }

    @Override
    public List<Recipe> getTensRecipeByPage(long page){
        List<Recipe> tenRecipes = new ArrayList<>();
        for(long i = page-1;i<page*10;i++){
            if(recipes.containsKey(i)){
                tenRecipes.add(recipes.get(i));
            }
        }
        return tenRecipes;
    }


}
