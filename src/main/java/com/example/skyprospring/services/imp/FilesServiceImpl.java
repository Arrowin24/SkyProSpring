package com.example.skyprospring.services.imp;

import com.example.skyprospring.services.FilesService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {
    @Value("${path.to.data.file}")
    private String dataFilePath;
    @Value("${name.of.ingredient.file}")
    private String ingredientsFileName;
    @Value("${name.of.recipe.file}")
    private String recipesFileName;

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

    @Override
    public void cleanIngredientFile() {
        cleanFile(ingredientsFileName);
    }

    @Override
    public void cleanRecipeFile() {
        cleanFile(recipesFileName);
    }

    @Override
    public File getRecipeDataFile() {
        return new File(dataFilePath + "/" + recipesFileName);
    }

    @Override
    public File getIngredientDataFile() {
        return new File(dataFilePath + "/" + ingredientsFileName);
    }

    @Override
    public File downloadRecipeFile() {
        File file = new File(dataFilePath + "/" + recipesFileName);
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }

    @Override
    public boolean uploadRecipeFile(MultipartFile file) {
        File recipeFile = getRecipeDataFile();
        if (recipeFile.exists()) {
            uploadFile(file, recipeFile);
            return true;
        }
        return false;
    }

    @Override
    public boolean uploadIngredientFile(MultipartFile file) {
        File ingredientFile = getIngredientDataFile();
        if (ingredientFile.exists()) {
            uploadFile(file, ingredientFile);
            return true;
        }
        return false;
    }

    private void uploadFile(MultipartFile fromFile, File toFile) {
        cleanIngredientFile();
        try (FileOutputStream fos = new FileOutputStream(toFile)) {
            IOUtils.copy(fromFile.getInputStream(), fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
