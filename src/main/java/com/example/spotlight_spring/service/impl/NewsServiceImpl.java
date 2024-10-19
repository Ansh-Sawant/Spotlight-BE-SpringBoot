package com.example.spotlight_spring.service.impl;

import com.example.spotlight_spring.dto.*;
import com.example.spotlight_spring.entity.Bookmark;
import com.example.spotlight_spring.entity.News;
import com.example.spotlight_spring.entity.User;
import com.example.spotlight_spring.repository.BookmarkRepository;
import com.example.spotlight_spring.repository.NewsRepository;
import com.example.spotlight_spring.repository.UserRepository;
import com.example.spotlight_spring.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    private final String apiKey = "1e212ce6230442a4ac98f2bacbab76b2";
    private final String newsApiUrl = "https://newsapi.org/v2/everything";

    @Override
    public NewsDTO[] getNews() {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(newsApiUrl)
                .queryParam("q", "india")
                .queryParam("apiKey", apiKey);

        ResponseEntity<NewsApiResponseDTO> response = restTemplate.getForEntity(uriBuilder.toUriString(), NewsApiResponseDTO.class);
        NewsApiResponseDTO newsApiResponse = response.getBody();

        if (newsApiResponse != null && newsApiResponse.getArticles() != null) {
            saveNews(newsApiResponse.getArticles());  // Save the articles, overwriting existing ones
            return newsApiResponse.getArticles();
        }

        return new NewsDTO[0];
    }

    private void saveNews(NewsDTO[] newsArray) {
        Arrays.stream(newsArray).forEach(newsDTO -> {
            // Map NewsDTO to News entity and save it
            News news = new News(
                    null,
                    newsDTO.getSource() != null ? newsDTO.getSource().getName() : null,
                    newsDTO.getAuthor(),
                    newsDTO.getTitle(),
                    newsDTO.getUrl(),
                    newsDTO.getUrlToImage(),
                    newsDTO.getPublishedAt(),
                    newsDTO.getContent()
            );
            newsRepository.save(news);
        });
    }

    @Override
    public String signupUser(SignupDTO signupDTO) {
        List<User> users = userRepository.findByEmail(signupDTO.getEmail());

        if (users.isEmpty()) {
            User user = new User(
                    null,
                    signupDTO.getName(),
                    signupDTO.getEmail(),
                    signupDTO.getPassword()
            );
            userRepository.save(user);
            return "Signup Successful";
        }

        return "Email Already Exist";
    }

    @Override
    public ResponseEntity<?> loginUser(LoginUserDTO loginUserDTO) {
        List<User> users = userRepository.findByEmail(loginUserDTO.getEmail());

        if (users.isEmpty()) {
            return ResponseEntity.ok("Invalid Email, User Not Found!");
        }

        User user = users.getFirst();  // Use the first user found

        if (user.getPassword().equals(loginUserDTO.getPassword())) {
            UserResponseDTO userResponseDTO = new UserResponseDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail()
            );
            return ResponseEntity.ok(userResponseDTO);
        } else {
            return ResponseEntity.ok("Invalid Credentials!");
        }
    }

    @Override
    public String bookmarkNews(BookmarkDTO bookmarkDTO) {
        // Map BookmarkDTO to Bookmark entity and save it
        Bookmark bookmark = new Bookmark(
                null,
                bookmarkDTO.getName(),
                bookmarkDTO.getEmail(),
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

    @Override
    public AllBookmarksDTO[] getAllBookmarkedNews() {
        List<Bookmark> allBookmarks = bookmarkRepository.findAll();

        if (!allBookmarks.isEmpty()) {
            return allBookmarks.stream()
                .map(bookmark -> new AllBookmarksDTO(
                        bookmark.getId(),
                        bookmark.getName(),
                        bookmark.getEmail(),
                        bookmark.getAuthor(),
                        bookmark.getTitle(),
                        bookmark.getDescription(),
                        bookmark.getUrl(),
                        bookmark.getUrlToImage(),
                        bookmark.getPublishedAt(),
                        bookmark.getContent()
                )).toArray(AllBookmarksDTO[]::new);
        }

        return new AllBookmarksDTO[0];
    }

    @Override
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
