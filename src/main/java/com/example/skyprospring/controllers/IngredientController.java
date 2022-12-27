package com.example.skyprospring.controllers;

import com.example.skyprospring.model.Ingredient;
import com.example.skyprospring.services.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/ingredient")
@RestController
@Tag(
        name = "Список ингредиентов",
        description = "CRUD-операции"
)
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping()
    @Operation(
            summary = "Создание ингредиента",
            description = "Создание нового ингредиента и добавление в список"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Создан ингредиент и добавлен в список"
                    )
            }
    )
    public ResponseEntity<Long> addIngredient(
            @RequestBody Ingredient ingredient)
    {
        long id = ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok(id);
    }


    @Operation(summary = "Получение ингредиент по id")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Создан ингредиент и добавлен в список",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Ingredient.class)
                                    )
                            }
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(
            @PathVariable int id)
    {
        Ingredient ingredient = ingredientService.getIngredientById(id);
        if (ingredient != null) {
            return ResponseEntity.ok(ingredient);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Получение ингредиентов",
            description = "Получение всех существующих ингредиентов"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получены все ингредиенты",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(
                                                    schema = @Schema(
                                                            implementation = Ingredient.class
                                                    )
                                            )
                                    )
                            }
                    )
            }
    )
    @GetMapping
    public ResponseEntity<Map<Long, Ingredient>> getAllIngredients() {
        Map<Long,Ingredient> ingredients = ingredientService.getAllIngredients();
        if (ingredients != null) {
            return ResponseEntity.ok(ingredients);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Корректировка ингредиента",
            description = "ВЫзов ингредиента по id и корректирвка его полей"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ингредиент имеет данный id и изменен",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Ingredient.class)
                                    )
                            }
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> editIngredient(
            @PathVariable long id,
            @RequestBody Ingredient newIngredient)
    {
        if (ingredientService.getIngredientById(id) != null) {
            Ingredient ingredient = ingredientService.editIngredient(id, newIngredient);
            return ResponseEntity.ok(ingredient);
        }
        return ResponseEntity.notFound().build();
    }
    @Operation(
            summary = "Удаление ингредиента из списка"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(
            @PathVariable long id)
    {
        if (ingredientService.removeIngredient(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
