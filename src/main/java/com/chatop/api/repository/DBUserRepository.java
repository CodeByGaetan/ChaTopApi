package com.chatop.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.chatop.api.model.DBUser;

public interface DBUserRepository extends CrudRepository<DBUser, Integer> {
    public DBUser findByEmail(String email);
}