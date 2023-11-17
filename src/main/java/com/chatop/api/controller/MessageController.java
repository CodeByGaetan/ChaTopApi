package com.chatop.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.request.MessageRequest;
import com.chatop.api.model.response.MessageResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "Messages", description = "API for CRUD operations on Messages")
@RestController
public class MessageController {
    @PostMapping("/messages")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageRequest messageRequest) {
        return ResponseEntity.ok(new MessageResponse("Message send with success"));
    }
}
