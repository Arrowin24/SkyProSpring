package com.example.skyprospring.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FirstController {
    @GetMapping
    public String helloWorld() {
        return "Приложение запущено";
    }

    @GetMapping("/info")
    public String info() {
        String name = "Igor Rudenko";
        String projectName = "SkyProSpring";
        String date = "08.12.2022";
        String description = "Я не знаю зачем я это сделал и почему на другой версии спринга ничего не работает...";
        return name + " " + projectName + " " + date + " " + description;
    }
}
