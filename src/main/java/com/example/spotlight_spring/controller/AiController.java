package com.example.spotlight_spring.controller;

import com.example.spotlight_spring.dto.AiQuestionDTO;
import com.example.spotlight_spring.dto.AiResponseDTO;
import com.example.spotlight_spring.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/ask")
    public ResponseEntity<?> askQuestion(@RequestBody AiQuestionDTO aiQuestionDTO) {
        try {
            AiResponseDTO response = geminiService.askGemini(
                    aiQuestionDTO.getQuestion(),
                    aiQuestionDTO.getArticleContent()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}