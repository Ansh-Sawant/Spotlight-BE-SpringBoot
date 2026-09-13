package com.example.spotlight_spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookmarks")
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String author;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String url;
    private String urlToImage;
    private String publishedAt;

    @Column(columnDefinition = "TEXT")
    private String content;
}
