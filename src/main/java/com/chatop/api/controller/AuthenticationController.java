package com.chatop.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.request.LoginRequest;
import com.chatop.api.model.request.RegisterRequest;
import com.chatop.api.model.response.JwtAuthenticationResponse;
import com.chatop.api.model.response.MessageResponse;
import com.chatop.api.service.AuthenticationService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Authentication", description = "API for authentication")
@RestController
public class AuthenticationController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/auth/register")
  public ResponseEntity<?> signup(@RequestBody RegisterRequest request) {
    JwtAuthenticationResponse response = authenticationService.signUp(request);
    // True if name, email or password is null
    if(response.getToken() == null) {
      return ResponseEntity.badRequest().body("{}");
    }
    return ResponseEntity.ok(response);
  }

  @PostMapping("/auth/login")
  public ResponseEntity<?> signin(@RequestBody LoginRequest request) {
    JwtAuthenticationResponse response = authenticationService.signIn(request);
    if(response.getToken() == null) {
      return ResponseEntity.status(401).body(new MessageResponse("error"));
    }
    return ResponseEntity.ok(response);
  }

}