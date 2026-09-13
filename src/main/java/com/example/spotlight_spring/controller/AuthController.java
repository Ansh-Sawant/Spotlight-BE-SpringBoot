package com.example.spotlight_spring.controller;

import com.example.spotlight_spring.dto.LoginUserDTO;
import com.example.spotlight_spring.dto.SignupDTO;
import com.example.spotlight_spring.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> signupUser(@RequestBody SignupDTO signupDTO) {
        String response = authService.signupUser(signupDTO);
        if(response.equals("Signup Successful")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserDTO loginUserDTO) {
        return authService.loginUser(loginUserDTO);
    }
}
