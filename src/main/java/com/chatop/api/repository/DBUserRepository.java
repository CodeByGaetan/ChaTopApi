package com.chatop.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatop.api.model.DBUser;

public interface DBUserRepository extends JpaRepository<DBUser, Integer> {
    public DBUser findByName(String name);
}