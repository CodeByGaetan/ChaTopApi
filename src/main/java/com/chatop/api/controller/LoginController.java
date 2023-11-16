package com.chatop.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.DBUser;
import com.chatop.api.security.dao.request.SignUpRequest;
import com.chatop.api.security.dao.response.JwtAuthenticationResponse;
import com.chatop.api.security.service.AuthenticationService;
import com.chatop.api.service.DBUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Users", description = "API for CRUD operations on Users")

@RestController
public class LoginController {

  // @Autowired
  // private DBUserService dbUserService;

  // @Operation(summary = "Create a user", description = "Create an user with an email, a name and a password. The response is the user object created.")
  // @PostMapping("/auth/register")
  // public DBUser registerDBUser(@RequestBody DBUser dbUser) {
  // return dbUserService.saveDBUser(dbUser);
  // }

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/auth/register")
  public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
      return ResponseEntity.ok(authenticationService.signup(request));
  }

}