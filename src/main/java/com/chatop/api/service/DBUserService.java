package com.chatop.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.api.model.DBUser;
import com.chatop.api.repository.DBUserRepository;

@Service
public class DBUserService {

    @Autowired
    private DBUserRepository dbUserRepository;

    public DBUser saveDBUser(DBUser dbUser) {
    return dbUserRepository.save(dbUser);
    }

}
