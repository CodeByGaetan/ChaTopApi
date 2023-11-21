package com.chatop.api.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.api.model.entity.Message;
import com.chatop.api.model.request.MessageRequest;
import com.chatop.api.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;
    
    public Message sendMessage(MessageRequest messageRequest) throws Exception {

        if (messageRequest.isNotValid()) {
            throw new Exception("Message request not valid");
        }

        Message message = new Message();

        message.setRental_id(messageRequest.getRental_id());
        message.setUser_id(messageRequest.getUser_id());
        message.setMessage(messageRequest.getMessage());
        message.setCreated_at(new Date(System.currentTimeMillis()));
        message.setUpdated_at(new Date(System.currentTimeMillis()));
        
        return messageRepository.save(message);
    }

}
