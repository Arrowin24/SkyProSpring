package com.example.skyprospring.controllers;

import com.example.skyprospring.model.Ingredient;
import com.example.skyprospring.services.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ingredient")
@RestController
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping()
    public ResponseEntity createIngredient(
            @RequestParam String name, @RequestParam int weight, @RequestParam String measureUnits)
    {
        Ingredient ingredient = new Ingredient(name, weight, measureUnits);
        ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok(ingredient);
    }


    @GetMapping("get")
    public ResponseEntity getIngredient(@RequestParam int ingredientId) {
        Ingredient ingredient = ingredientService.getIngredientById(ingredientId);
                if (ingredient == null) {
                    return ResponseEntity.notFound().build();
                }
        return ResponseEntity.ok(ingredient);
    }
}
