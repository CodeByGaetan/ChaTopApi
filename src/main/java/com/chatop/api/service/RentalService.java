package com.chatop.api.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.chatop.api.model.DBUser;
import com.chatop.api.model.Rental;
import com.chatop.api.model.request.RentalRequest;
import com.chatop.api.repository.DBUserRepository;
import com.chatop.api.repository.RentalRepository;

@Service
public class RentalService {
    
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private DBUserRepository userRepository;

    public Iterable<Rental> getRentals() {
        return rentalRepository.findAll();
    };

    public Rental createRental(RentalRequest rentalRequest) {

        Rental rental = new Rental();
        rental.setName(rentalRequest.getName());
        rental.setSurface(rentalRequest.getSurface());
        rental.setPrice(rentalRequest.getPrice());
        rental.setDescription(rentalRequest.getDescription());
        rental.setPicture("A implementer");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        DBUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        rental.setOwner_id(user.getId());

        rental.setCreated_at(new Date(System.currentTimeMillis()));
        rental.setUpdated_at(new Date(System.currentTimeMillis()));

        return rentalRepository.save(rental);
    }
}
