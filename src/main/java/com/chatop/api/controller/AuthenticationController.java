package com.chatop.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.DBUser;
import com.chatop.api.model.dao.request.SignInRequest;
import com.chatop.api.model.dao.request.SignUpRequest;
import com.chatop.api.model.dao.response.JwtAuthenticationResponse;
import com.chatop.api.model.dao.response.MessageResponse;
import com.chatop.api.service.AuthenticationService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Users", description = "API for CRUD operations on Users")
@RestController
public class AuthenticationController {

  @Autowired
  private AuthenticationService authenticationService;

  @GetMapping("/auth/users")
  public Iterable<DBUser> getAllUsers() {
    return authenticationService.getAllUsers();
  }

  @PostMapping("/auth/register")
  public ResponseEntity<?> signup(@RequestBody SignUpRequest request) {
    JwtAuthenticationResponse response = authenticationService.signUp(request);
    // True if name, email or password is null
    if(response.getToken() == null) {
      return ResponseEntity.badRequest().body("{}");
    }
    return ResponseEntity.ok(response);
  }

  @PostMapping("/auth/login")
  public ResponseEntity<?> signin(@RequestBody SignInRequest request) {
    JwtAuthenticationResponse response = authenticationService.signIn(request);
    if(response.getToken() == null) {
      return ResponseEntity.status(401).body(new MessageResponse("error"));
    }
    return ResponseEntity.ok(response);
  }

  // @GetMapping("/auth/me")
  // public ResponseEntity<?> myInfo() {

  // }

}