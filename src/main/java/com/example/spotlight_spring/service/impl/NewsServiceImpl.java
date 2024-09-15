package com.example.spotlight_spring.service.impl;

import com.example.spotlight_spring.dto.NewsApiResponseDTO;
import com.example.spotlight_spring.dto.NewsDTO;
import com.example.spotlight_spring.entity.News;
import com.example.spotlight_spring.repository.NewsRepository;
import com.example.spotlight_spring.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

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
}
