package com.example.skyprospring.controllers;
import com.example.skyprospring.model.Recipe;
import com.example.skyprospring.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/recipe")
@RestController
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @GetMapping()
    public ResponseEntity<Map<Long, Recipe>> getAllIngredients() {
        if (recipeService.getAllRecipes().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/tenPerPage/{page}")
    public ResponseEntity<List<Recipe>> getRecipesByTensPerPage(@PathVariable long page) {
        List<Recipe> recipes = recipeService.getTensRecipeByPage(page);
        if (recipes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/sorted/byIngredients")
    public ResponseEntity<List<Recipe>> getRecipesByIds(@RequestParam List<Long> ids) {
        if (recipeService.getRecipesByIngredientsId(ids).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Recipe> recipes = recipeService.getRecipesByIngredientsId(ids);
        return ResponseEntity.ok(recipes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> editIngredient(@PathVariable long id, @RequestBody Recipe newRecipe) {
        if (recipeService.getRecipeById(id) != null) {
            Recipe recipe = recipeService.editRecipe(id, newRecipe);
            return ResponseEntity.ok(recipe);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        if (recipeService.removeRecipe(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
