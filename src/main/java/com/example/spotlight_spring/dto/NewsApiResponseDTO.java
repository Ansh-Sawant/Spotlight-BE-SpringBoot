package com.example.spotlight_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsApiResponseDTO {
    private String status;
    private int totalResults;
    private NewsDTO[] articles;  // The articles field is an array of NewsDTO
}
