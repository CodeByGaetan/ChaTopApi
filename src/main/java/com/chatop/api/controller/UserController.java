package com.chatop.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.response.UserResponse;
import com.chatop.api.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Users", description = "API for user information")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/auth/me")
    public UserResponse myInfo() {
    return userService.getCurrentUserInfo();
    }
    
    @GetMapping("/user/{id}")
    public UserResponse getUserInfoById(@PathVariable("id") final Integer id) {
        return userService.getUserInfoById(id);
    }
    
}