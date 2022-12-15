package com.example.skyprospring.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
public class Recipe {
    private String name;
    private int time;  //minutes
    private List<Ingredient> ingredients;
    private LinkedList<String> steps;
}
