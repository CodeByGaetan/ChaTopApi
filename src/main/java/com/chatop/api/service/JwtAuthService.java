package com.chatop.api.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.api.model.entity.DBUser;
import com.chatop.api.model.request.LoginRequest;
import com.chatop.api.model.request.RegisterRequest;
import com.chatop.api.model.response.JwtAuthResponse;
import com.chatop.api.repository.UserRepository;

@Service
public class JwtAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtAuthResponse signUp(RegisterRequest request) throws Exception {

        if (request.getName() == null || request.getEmail() == null || request.getPassword() == null) {
            throw new Exception("name, email or password is null");
        }

        DBUser user = new DBUser();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreated_at(new Date(System.currentTimeMillis()));
        user.setUpdated_at(new Date(System.currentTimeMillis()));

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new Exception("email already exists");
        }

        String jwt = jwtService.generateToken(user);

        return JwtAuthResponse.builder().token(jwt).build();
    }

    public JwtAuthResponse signIn(LoginRequest request) throws Exception {

        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            throw new Exception("Wrong credentials or user not exists");
        }

        DBUser user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new Exception("Invalid email or password."));

        String jwt = jwtService.generateToken(user);

        return JwtAuthResponse.builder().token(jwt).build();
    }

}