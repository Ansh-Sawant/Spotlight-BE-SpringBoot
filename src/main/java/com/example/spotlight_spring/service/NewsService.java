package com.example.spotlight_spring.service;

import com.example.spotlight_spring.dto.BookmarkDTO;
import com.example.spotlight_spring.dto.NewsDTO;
import com.example.spotlight_spring.dto.LoginUserDTO;
import com.example.spotlight_spring.dto.SignupDTO;
import org.springframework.http.ResponseEntity;

public interface NewsService {

    NewsDTO[] getNews();

    String signupUser(SignupDTO signupDTO);

    ResponseEntity<?> loginUser(LoginUserDTO loginUserDTO);

    String bookmarkNews(BookmarkDTO bookmarkDTO);

}
