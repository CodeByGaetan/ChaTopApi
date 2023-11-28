package com.chatop.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.response.UserResponse;
import com.chatop.api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Users", description = "API for user information")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get current user info")
    @GetMapping("/auth/me")
    public UserResponse getCurrentUserInfo() {
        return userService.getCurrentUserInfo();
    }

    @Operation(summary = "Get user info by Id")
    @GetMapping("/user/{id}")
    public UserResponse getUserInfoById(@PathVariable("id") final Integer id) {
        return userService.getUserInfoById(id);
    }

}