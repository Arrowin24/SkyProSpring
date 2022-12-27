package com.example.skyprospring.services.imp;

import com.example.skyprospring.services.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {
    @Value("${path.to.data.file}") private String dataFilePath;
    @Value("${name.of.ingredient.file}") private String ingredientsFileName;
    @Value("${name.of.recipe.file}") private String recipesFileName;

    @PostConstruct
    private void init() {
        try {
            if (!Files.exists(Path.of(dataFilePath, ingredientsFileName))) {
                Files.createFile(Path.of(dataFilePath, ingredientsFileName));
            }
            if (!Files.exists(Path.of(dataFilePath, recipesFileName))) {
                Files.createFile(Path.of(dataFilePath, recipesFileName));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean saveIngredientToFile(String json) {
        return saveToFile(ingredientsFileName, json);
    }

    @Override
    public String readIngredientsFromFile() {
        return readFromFile(ingredientsFileName);
    }

    @Override
    public boolean saveRecipesToFile(String json) {
        return saveToFile(recipesFileName, json);
    }

    @Override
    public String readRecipesFromFile() {
        return readFromFile(recipesFileName);
    }

    private boolean saveToFile(String fileName, String json) {
        try {
            cleanFile(fileName);
            Files.writeString(Path.of(dataFilePath, fileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(dataFilePath, fileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void cleanFile(String fileName) {
        try {
            Files.deleteIfExists(Path.of(dataFilePath, fileName));
            Files.createFile(Path.of(dataFilePath, fileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
