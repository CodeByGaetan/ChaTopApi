package com.chatop.api.model.dao.request;

import lombok.Data;

@Data
public class SignUpRequest {

    private String name;

    private String email;

    private String password;

    public boolean isNotValid() {
        return name == null || email == null || password == null;
    }
}
