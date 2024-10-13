package com.example.spotlight_spring.repository;

import com.example.spotlight_spring.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
