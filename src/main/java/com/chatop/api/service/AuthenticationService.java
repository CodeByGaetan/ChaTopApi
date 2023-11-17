package com.chatop.api.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.api.model.DBUser;
import com.chatop.api.model.dao.request.SignInRequest;
import com.chatop.api.model.dao.request.SignUpRequest;
import com.chatop.api.model.dao.response.JwtAuthenticationResponse;
import com.chatop.api.repository.DBUserRepository;

@Service
public class AuthenticationService {

    @Autowired
    private DBUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Iterable<DBUser> getAllUsers() {
        return userRepository.findAll();
    }

    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        // Check if name, email or password is not null
        if (request.isNotValid()) {
            return JwtAuthenticationResponse.builder().build();
        }
        // Enregistrement de l'utilisateur dans la BDD
        DBUser user = new DBUser();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreated_at(new Date(System.currentTimeMillis()));
        user.setUpdated_at(new Date(System.currentTimeMillis()));

        // Check if email already exists
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return JwtAuthenticationResponse.builder().build();
        }
        
        // Génération du JWT
        var jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        // Check if good credentials or User exists
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch(Exception e) {
            return JwtAuthenticationResponse.builder().build();
        }
        // Recupération de l'utilsateur et génération du JWT
        DBUser user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        String jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    // public JwtAuthenticationResponse myInfo(SignInRequest request) {
    //     try {
    //         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    //     } catch(Exception e) {
    //         return JwtAuthenticationResponse.builder().build();
    //     }
    //     DBUser user = userRepository.findByEmail(request.getEmail())
    //             .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
    //     String jwt = jwtService.generateToken(user);
    //     return JwtAuthenticationResponse.builder().token(jwt).build();
    // }



    
}
