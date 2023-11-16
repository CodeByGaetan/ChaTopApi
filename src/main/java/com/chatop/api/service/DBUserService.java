package com.chatop.api.service;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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

    // public DBUser registerNewDbUser(UserDto userDto) throws Exception {
    //     if (checkIfUserExist(userDto.getEmail())) {
    //         // throw new UserAlreadyExistException("User already exists for this email");
    //         throw new Exception("User Already exists !");
    //     } 
    // }

    // public boolean checkIfUserExist(String email) {
    //     return dbUserRepository.findByEmail(email) != null ? true : false;
    // }

}
