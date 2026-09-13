package com.example.spotlight_spring.controller;

import com.example.spotlight_spring.dto.BookmarksDTO;
import com.example.spotlight_spring.dto.BookmarkDTO;
import com.example.spotlight_spring.dto.DeleteBookmarkDTO;
import com.example.spotlight_spring.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @PostMapping("/bookmarks")
    public String bookmarkNews(@RequestBody BookmarkDTO bookmarkDTO) {
        return bookmarkService.bookmarkNews(bookmarkDTO);
    }

    @GetMapping("/bookmarkedNews")
    public List<BookmarksDTO> getBookmarkedNews() {
        return bookmarkService.getBookmarkedNews();
    }

    @PostMapping("/deleteBookmarks")
    public String deleteBookmarks(@RequestBody DeleteBookmarkDTO deleteBookmarkDTO) {
        return bookmarkService.deleteBookmarkedNews(deleteBookmarkDTO);
    }
}
