package com.chatop.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.chatop.api.model.Message;

public interface MessageRepository extends CrudRepository<Message, Integer>{
    
}
