package com.example.spotlight_spring.service;

import com.example.spotlight_spring.dto.BookmarksDTO;
import com.example.spotlight_spring.dto.BookmarkDTO;
import com.example.spotlight_spring.dto.DeleteBookmarkDTO;
import com.example.spotlight_spring.entity.Bookmark;
import com.example.spotlight_spring.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    public String bookmarkNews(BookmarkDTO bookmarkDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        System.out.println("\nEmail: " + email);
        Bookmark bookmark = new Bookmark(
                null,
                email,
                bookmarkDTO.getAuthor(),
                bookmarkDTO.getTitle(),
                bookmarkDTO.getDescription(),
                bookmarkDTO.getUrl(),
                bookmarkDTO.getUrlToImage(),
                bookmarkDTO.getPublishedAt(),
                bookmarkDTO.getContent()
        );
        bookmarkRepository.save(bookmark);
        return "Bookmark Saved Successfully";
    }

    public List<BookmarksDTO> getBookmarkedNews() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<Bookmark> bookmarks = bookmarkRepository.findByEmail(email);

        if (!bookmarks.isEmpty()) {
            return bookmarks.stream()
                    .map(bookmark -> new BookmarksDTO(
                            bookmark.getId(),
                            bookmark.getEmail(),
                            bookmark.getAuthor(),
                            bookmark.getTitle(),
                            bookmark.getDescription(),
                            bookmark.getUrl(),
                            bookmark.getUrlToImage(),
                            bookmark.getPublishedAt(),
                            bookmark.getContent()
                    ))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public String deleteBookmarkedNews(DeleteBookmarkDTO deleteBookmarkDTO) {
        Long deleteId = deleteBookmarkDTO.getId();
        if (deleteId == null) {
            throw new IllegalArgumentException("Bookmark ID must not be null");
        }
        try {
            bookmarkRepository.deleteById(deleteId);
            return "Bookmark Deleted Successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error deleting bookmark: " + e.getMessage(), e);
        }
    }
}
