package com.wydex.posterapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelathCheckController {

    @GetMapping("/health")
    public String healthCheck() {
        return "Health-Check Success...";
    }
}
