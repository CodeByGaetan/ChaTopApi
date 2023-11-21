package com.chatop.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.response.UserResponse;
import com.chatop.api.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/user/{id}")
    public UserResponse getUserById(@PathVariable("id") final Integer id) {
        return userService.getUserById(id);
    }
    
}
