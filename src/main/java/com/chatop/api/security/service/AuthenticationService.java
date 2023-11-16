package com.chatop.api.security.service;

import com.chatop.api.security.dao.request.SignUpRequest;
import com.chatop.api.security.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);
}