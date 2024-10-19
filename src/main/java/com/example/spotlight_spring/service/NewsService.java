package com.example.spotlight_spring.service;

import com.example.spotlight_spring.dto.*;
import com.example.spotlight_spring.entity.Bookmark;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NewsService {

    NewsDTO[] getNews();

    String signupUser(SignupDTO signupDTO);

    ResponseEntity<?> loginUser(LoginUserDTO loginUserDTO);

    String bookmarkNews(BookmarkDTO bookmarkDTO);

    AllBookmarksDTO[] getAllBookmarkedNews();

    String deleteBookmarkedNews(DeleteBookmarkDTO deleteBookmarkDTO);

}
