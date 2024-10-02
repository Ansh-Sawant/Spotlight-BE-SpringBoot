package com.example.spotlight_spring.repository;

import com.example.spotlight_spring.entity.News;
import com.example.spotlight_spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
