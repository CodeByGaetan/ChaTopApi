package com.chatop.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chatop.api.model.entity.DBUser;

@Repository
public interface UserRepository extends CrudRepository<DBUser, Integer> {
    public Optional<DBUser> findByEmail(String email);
}