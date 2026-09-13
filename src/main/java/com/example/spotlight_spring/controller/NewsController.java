package com.example.spotlight_spring.controller;

import com.example.spotlight_spring.dto.*;
import com.example.spotlight_spring.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/ping")
    public String checkApp() {
        return "Service is Up!";
    }

    @GetMapping("/news")
    public NewsDTO[] getNews(){
        return newsService.getNews();
    }

}
