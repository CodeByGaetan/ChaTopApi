package com.chatop.api.model.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    public boolean isNotValid() {
        return name == null || email == null || password == null;
    }
}