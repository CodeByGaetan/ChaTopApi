package com.chatop.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatop.api.model.DBUser;

public interface DBUserRepository extends JpaRepository<DBUser, Integer> {
    Optional<DBUser> findByEmail(String email);
}