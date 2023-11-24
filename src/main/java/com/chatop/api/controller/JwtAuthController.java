package com.chatop.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.request.LoginRequest;
import com.chatop.api.model.request.RegisterRequest;
import com.chatop.api.model.response.JwtAuthResponse;
import com.chatop.api.model.response.MessageResponse;
import com.chatop.api.service.JwtAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Authentication", description = "API for authentication operations")
@RestController
public class JwtAuthController {

  @Autowired
  private JwtAuthService authenticationService;

  @Operation(summary = "Sign up a user")
  @PostMapping("/auth/register")
  public ResponseEntity<?> signup(@RequestBody RegisterRequest request) {
    try {
      JwtAuthResponse response = authenticationService.signUp(request);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("{}");
    }
  }

  @Operation(summary = "Sign in a user")
  @PostMapping("/auth/login")
  public ResponseEntity<?> signin(@RequestBody LoginRequest request) {
    try {
      JwtAuthResponse response = authenticationService.signIn(request);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.status(401).body(new MessageResponse("error"));
    }
  }

}