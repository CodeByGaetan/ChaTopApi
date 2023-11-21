package com.chatop.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.chatop.api.model.entity.DBUser;
import com.chatop.api.model.response.UserResponse;
import com.chatop.api.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                Optional<DBUser> user = userRepository.findByEmail(username);
                return new User(user.get().getEmail(), user.get().getPassword(), getGrantedAuthorities("ROLE"));
            }
        };
    }

    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }

    public UserResponse getUserInfoById(Integer id) {
        DBUser user = userRepository.findById(id).orElseThrow();
        return generateUserResponse(user);
    }

    public UserResponse getCurrentUserInfo() {
        DBUser user = getCurrentUser();
        return generateUserResponse(user);
    }

    public UserResponse generateUserResponse(DBUser user) {
        UserResponse response = new UserResponse();
        response.setID(user.getId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setCreated_at(user.getCreated_at());
        response.setUpdated_at(user.getUpdated_at());
        return response;
    }

    public DBUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        DBUser user = userRepository.findByEmail(userEmail).orElseThrow();
        return user;
    }

}