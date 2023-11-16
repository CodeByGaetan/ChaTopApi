package com.chatop.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.security.dao.request.SignInRequest;
import com.chatop.api.security.dao.request.SignUpRequest;
import com.chatop.api.security.dao.response.JwtAuthenticationResponse;
import com.chatop.api.security.service.AuthenticationService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Users", description = "API for CRUD operations on Users")
@RestController
public class LoginController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/auth/register")
  public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
    return ResponseEntity.ok(authenticationService.signup(request));
  }

  @PostMapping("/auth/login")
  public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request) {
    return ResponseEntity.ok(authenticationService.signin(request));
  }

}