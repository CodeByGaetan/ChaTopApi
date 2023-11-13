package com.chatop.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.DBUser;
import com.chatop.api.repository.DBUserRepository;

@RestController
public class LoginController {

    @Autowired
    private DBUserRepository userRepository;

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<DBUser> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @GetMapping("/user")
    public String getUser() {
        return "Welcome, DB User !";
    }

}