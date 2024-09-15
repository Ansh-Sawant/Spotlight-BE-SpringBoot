package com.example.spotlight_spring.repository;

import com.example.spotlight_spring.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
