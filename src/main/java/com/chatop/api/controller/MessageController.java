package com.chatop.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.request.MessageRequest;
import com.chatop.api.model.response.MessageResponse;
import com.chatop.api.service.MessageService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "Messages", description = "API for CRUD operations on Messages")
@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/messages")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequest messageRequest) {

        try {
            messageService.sendMessage(messageRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{}");
        }
        
        return ResponseEntity.ok(new MessageResponse("Message send with success"));
    }

}
