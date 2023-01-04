package com.example.skyprospring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    private String name;
    private int weight;
    private String measureUnits;

    @Override
    public String toString() {
        return name + " - " + weight + " " + measureUnits;
    }
}
