package com.chatop.api.model.request;

import lombok.Data;

@Data
public class MessageRequest {
    private String message;
    private Integer user_id;
    private Integer rental_id;
   
    public boolean isNotValid() {
        return message == null || user_id == null || rental_id == null;
    }
}