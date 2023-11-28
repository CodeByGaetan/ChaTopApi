package com.chatop.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.request.MessageRequest;
import com.chatop.api.model.response.MessageResponse;
import com.chatop.api.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Messages", description = "API for CRUD operations on Messages")
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Operation(summary = "Create a message")
    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(HttpServletResponse response, @RequestBody MessageRequest messageRequest) {
        try {
            messageService.createMessage(messageRequest);
            return ResponseEntity.ok(new MessageResponse("Message sent with success"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{}");
        }
    }

}