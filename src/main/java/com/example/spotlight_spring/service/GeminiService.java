package com.example.spotlight_spring.service;

import com.example.spotlight_spring.dto.AiResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    public AiResponseDTO askGemini(String question, String articleContent) {
        RestTemplate restTemplate = new RestTemplate();
        String input = """
                You are Spotlight AI, an assistant that answers questions about news articles.
                Use only the article provided below to answer the user's question.
                If the answer cannot be found in the article, say so clearly.

                Article:
                %s

                User Question:
                %s
                """.formatted(articleContent, question);
        String requestBody = """
                {
                  "model": "gemini-3.6-flash",
                  "input": %s
                }
                """.formatted(toJsonString(input));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", apiKey);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                geminiApiUrl,
                HttpMethod.POST,
                request,
                String.class
        );
        return extractAnswer(response.getBody());
    }

    private AiResponseDTO extractAnswer(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode steps = root.path("steps");
            for (JsonNode step : steps) {
                if ("model_output".equals(step.path("type").asText())) {
                    String answer = step.path("content").get(0).path("text").asText();
                    return new AiResponseDTO(answer);
                }
            }
            return new AiResponseDTO("No AI response generated.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Gemini response", e);
        }
    }

    private String toJsonString(String value) {
        return "\"" + value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r") + "\"";
    }
}