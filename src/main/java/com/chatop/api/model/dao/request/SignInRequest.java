package com.chatop.api.model.dao.request;

// import lombok.AllArgsConstructor;
// import lombok.Builder;
import lombok.Data;
// import lombok.NoArgsConstructor;

@Data
public class SignInRequest {
    private String email;
    private String password;
}