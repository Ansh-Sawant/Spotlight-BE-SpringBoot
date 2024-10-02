package com.example.spotlight_spring.service;

import com.example.spotlight_spring.dto.NewsDTO;
import com.example.spotlight_spring.dto.LoginUserDTO;
import com.example.spotlight_spring.dto.SignupDTO;

public interface NewsService {

    NewsDTO[] getNews();

    String signupUser(SignupDTO signupDTO);

    String loginUser(LoginUserDTO loginUserDTO);

}
