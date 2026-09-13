package com.example.spotlight_spring.service;

import com.example.spotlight_spring.dto.AuthResponseDTO;
import com.example.spotlight_spring.dto.LoginUserDTO;
import com.example.spotlight_spring.dto.SignupDTO;
import com.example.spotlight_spring.dto.UserResponseDTO;
import com.example.spotlight_spring.entity.User;
import com.example.spotlight_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public String signupUser(SignupDTO signupDTO) {
        List<User> users = userRepository.findByEmail(signupDTO.getEmail());
        if (users.isEmpty()) {
            String encodedPassword = passwordEncoder.encode(signupDTO.getPassword());
            User user = new User(
                    null,
                    signupDTO.getName(),
                    signupDTO.getEmail(),
                    encodedPassword
            );
            userRepository.save(user);
            return "Signup Successful";
        }
        return "Email Already Exist";
    }

    public ResponseEntity<?> loginUser(LoginUserDTO loginUserDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUserDTO.getEmail(),
                            loginUserDTO.getPassword()
                    )
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            List<User> users = userRepository.findByEmail(loginUserDTO.getEmail());
            User user = users.get(0);
            String token = jwtService.generateToken(userDetails);
            AuthResponseDTO authResponseDTO = new AuthResponseDTO(
                    token,
                    user.getId(),
                    user.getName(),
                    user.getEmail()
            );
            return ResponseEntity.ok(authResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.ok("Invalid Credentials!");
        }
    }
}