package com.chatop.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.chatop.api.model.DBUser;
import com.chatop.api.repository.DBUserRepository;

@Service
public class UserService {
    @Autowired
    private DBUserRepository userRepository;

    public UserDetailsService userDetailsService() {

        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                // return userRepository.findByEmail(username)
                //         .orElseThrow(() -> new UsernameNotFoundException("User not found"));
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
}