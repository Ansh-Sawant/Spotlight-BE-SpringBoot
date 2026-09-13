package com.example.spotlight_spring.repository;

import com.example.spotlight_spring.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByEmail(String email);
}
