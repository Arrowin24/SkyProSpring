package com.example.skyprospring.controllers;

import com.example.skyprospring.model.Recipe;
import com.example.skyprospring.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(
        name = "Список рецептов",
        description = "CRUD-операции над списком рецептов"
)
@RequestMapping("/recipe")
@RestController
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(
            summary = "Создаение рецепта",
            description = "СМоздание и добавление в коллекцию рецепта"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Создан новый рецепт",
                            content = {
                                    @Content(
                                            mediaType = "application/json"
                                    )
                            }
                    ), @ApiResponse(
                    responseCode = "404",
                    description = "Скорее всего не указано имя рецепта"
            )
            }
    )
    @PostMapping
    public ResponseEntity<Long> createRecipe(
            @RequestBody Recipe recipe)
    {
        if (StringUtils.isNoneBlank(recipe.getName())) {
            long id = recipeService.addRecipe(recipe);
            return ResponseEntity.ok(id);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Получение рецепта по id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получен ингредиент под конкретным id",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = Recipe.class
                                            )
                                    )
                            }
                    ), @ApiResponse(
                    responseCode = "404",
                    description = "Скорее всего нет рецепта  с таким id"
            )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(
            @PathVariable int id)
    {
        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @Operation(
            summary = "Получение всех рецептов"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получены все рецепты",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(
                                                    schema = @Schema(
                                                            implementation = Recipe.class
                                                    )
                                            )
                                    )
                            }
                    ), @ApiResponse(
                    responseCode = "404",
                    description = "Скорее всего нет ни одного рецепта"
            )
            }
    )
    @GetMapping()
    public ResponseEntity<Map<Long, Recipe>> getAllIngredients() {
        if (recipeService.getAllRecipes().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @Operation(
            summary = "Вывод по 10 рецептов на странице",
            description = "Указывая номер страницы, получаем 10 рецептов на этой страницы"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получены все рецепты",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(
                                                    schema = @Schema(
                                                            implementation = Recipe.class
                                                    )
                                            )
                                    )
                            }
                    ), @ApiResponse(
                    responseCode = "404",
                    description = "Скорее всего нет ингредиента с таким id"
            )
            }
    )
    @Parameters(
            @Parameter(
                    name = "page",
                    examples = {
                            @ExampleObject(name = "1"), @ExampleObject(name = "2")
                    },
                    description = "Страницы нужно указывать с 0 и т.д."
            )
    )
    @GetMapping("/tenPerPage/{page}")
    public ResponseEntity<List<Recipe>> getRecipesByTensPerPage(
            @PathVariable long page)
    {
        List<Recipe> recipes = recipeService.getTensRecipeByPage(page);
        if (recipes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipes);
    }

    @Operation(
            summary = "Сортирвка рецептов по id ингредиентов",
            description = "Указывая id ингредиентов, можно получить список рецептов с данными ингредиентами"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получены все рецепты",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(
                                                    schema = @Schema(
                                                            implementation = Recipe.class
                                                    )
                                            )
                                    )
                            }
                    ), @ApiResponse(
                    responseCode = "404",
                    description = "Скорее всего нет ни одного рецепта с такими игредиентами"
            )
            }
    )
    @GetMapping("/sorted/byIngredients")
    public ResponseEntity<List<Recipe>> getRecipesByIds(
            @RequestParam List<Long> ids)
    {
        if (recipeService.getRecipesByIngredientsId(ids).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Recipe> recipes = recipeService.getRecipesByIngredientsId(ids);
        return ResponseEntity.ok(recipes);
    }

    @Operation(
            summary = "Редактирование рецепта по id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Рецепт редактирован",
                            content = {
                                    @Content(
                                            mediaType = "application/json"
                                    )
                            }
                    ), @ApiResponse(
                    responseCode = "404",
                    description = "Скорее всего указано неверное id рецепта"
            )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> editIngredient(
            @PathVariable long id,
            @RequestBody Recipe newRecipe)
    {
        if (recipeService.getRecipeById(id) != null) {
            Recipe recipe = recipeService.editRecipe(id, newRecipe);
            return ResponseEntity.ok(recipe);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Удаление рецепта"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(
            @PathVariable long id)
    {
        if (recipeService.removeRecipe(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
