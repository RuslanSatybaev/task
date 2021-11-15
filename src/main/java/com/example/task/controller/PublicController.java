package com.example.task.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publicr")
public class PublicController {

    @GetMapping
    public String getMessage() {
        return "{\n" +
                "    token: \"тут сгенерированный токен\"\n" +
                "}";
    }
}