package com.chatop.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.auth.JwtUtil;
import com.chatop.api.model.DBUser;
import com.chatop.api.model.request.LoginReq;
import com.chatop.api.model.response.ErrorRes;
import com.chatop.api.model.response.LoginRes;
import com.chatop.api.service.DBUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Users", description = "API for CRUD operations on Users")

@RestController
public class LoginController {

  @Autowired
  private DBUserService dbUserService;

  @Autowired
  AuthenticationManager authenticationManager;


  @Autowired
  JwtUtil jwtUtil;

  // @GetMapping("/all")
  // public Iterable<DBUser> getAll() {
  // return dbUserService.getAll();
  // }

  @Operation(summary = "Create a user", description = "Create an user with an email, a name and a password. The response is the user object created.")
  @PostMapping("/auth/login")
  public ResponseEntity login(@RequestBody LoginReq loginReq) {
    
    try {
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
      String email = authentication.getName();
      DBUser user = new DBUser();
      String token = jwtUtil.createToken(user);
      LoginRes loginRes = new LoginRes(email, token);

      return ResponseEntity.ok(loginRes);

    } catch (BadCredentialsException e) {
      ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "Invalid username or password");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    } catch (Exception e) {
      ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
  }

  // @PostMapping("/auth/register")
  // public DBUser registerDBUser(@RequestBody DBUser dbUser) {
  // return dbUserService.saveDBUser(dbUser);
  // }

  // @PostMapping("/auth/register")
  // public DBUser registerDBUser(@RequestBody @Valid UserDto userDto) {

  // try {
  // DBUser registered = dbUserService.registerNewDbUser(userDto);
  // } catch (Exception exception) {
  // mav.addObject("message", "An account for that username/email already
  // exists.");
  // return mav;
  // }

  // return rege
  // }

}