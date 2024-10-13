package com.example.spotlight_spring.repository;

import com.example.spotlight_spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
}
