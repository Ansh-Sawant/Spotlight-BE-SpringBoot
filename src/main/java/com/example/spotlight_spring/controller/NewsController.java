package com.example.spotlight_spring.controller;

import com.example.spotlight_spring.dto.*;
import com.example.spotlight_spring.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public NewsDTO[] getNews(){
        return newsService.getNews();
    }

    @PostMapping("/register")
    public ResponseEntity<String> signupUser(@RequestBody SignupDTO signupDTO) {
        String response = newsService.signupUser(signupDTO);
        if(response.equals("Signup Successful")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserDTO loginUserDTO) {
        return newsService.loginUser(loginUserDTO);
    }

    @PostMapping("/bookmarks")
    public String bookmarkNews(@RequestBody BookmarkDTO bookmarkDTO) {
        return newsService.bookmarkNews(bookmarkDTO);
    }

    @GetMapping("/bookmarkedNews")
    public AllBookmarksDTO[] getAllBookmarkedNews() {
        return newsService.getAllBookmarkedNews();
    }

    @PostMapping("/deleteBookmarks")
    public String deleteBookmarks(@RequestBody DeleteBookmarkDTO deleteBookmarkDTO) {
        return newsService.deleteBookmarkedNews(deleteBookmarkDTO);
    }
}
