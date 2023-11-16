package com.chatop.api.controller;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.DBUser;
import com.chatop.api.model.User;
import com.chatop.api.security.dao.request.SignInRequest;
import com.chatop.api.security.dao.request.SignUpRequest;
import com.chatop.api.security.dao.response.JwtAuthenticationResponse;
import com.chatop.api.security.service.AuthenticationService;
import com.chatop.api.service.DBUserService;
import com.chatop.api.service.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Users", description = "API for CRUD operations on Users")

@RestController
public class LoginController {

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/auth/users")
  public Iterable<User> getAll() {
    return userRepository.findAll();
  }

  @PostMapping("/auth/register")
  public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
    return ResponseEntity.ok(authenticationService.signup(request));
  }

  @PostMapping("/auth/login")
  public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request) {
    return ResponseEntity.ok(authenticationService.signin(request));
  }

}