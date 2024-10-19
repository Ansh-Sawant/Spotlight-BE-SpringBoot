package com.example.spotlight_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBookmarkDTO {
    private Long id;
    private String email;
    private String title;
}
