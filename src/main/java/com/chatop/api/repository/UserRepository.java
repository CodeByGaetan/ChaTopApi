package com.chatop.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatop.api.model.DBUser;

public interface UserRepository extends JpaRepository<DBUser, Integer> {
    Optional<DBUser> findByEmail(String email);
}