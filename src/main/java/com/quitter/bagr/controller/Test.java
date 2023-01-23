package com.quitter.bagr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @GetMapping("/api")
    public String handleApi() {
        return "I am happy!";
    }
}
