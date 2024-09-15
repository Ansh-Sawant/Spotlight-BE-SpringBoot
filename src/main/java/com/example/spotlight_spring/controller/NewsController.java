package com.example.spotlight_spring.controller;

import com.example.spotlight_spring.dto.NewsDTO;
import com.example.spotlight_spring.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NewsController {

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public NewsDTO[] getNews(){
        return newsService.getNews();
    }
}
