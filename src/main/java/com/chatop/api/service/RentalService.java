package com.chatop.api.service;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chatop.api.model.entity.DBUser;
import com.chatop.api.model.entity.Rental;
import com.chatop.api.model.request.RentalRequest;
import com.chatop.api.model.response.RentalsResponse;
import com.chatop.api.repository.RentalRepository;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    public RentalsResponse getRentals() {
        Iterable<Rental> rentalList = rentalRepository.findAll();
        return new RentalsResponse(rentalList);
    }

    public Optional<Rental> getRental(Integer id) {
        return rentalRepository.findById(id);
    }

    public Rental createRental(RentalRequest rentalRequest) {
        Rental rental = new Rental();
        rental.setName(rentalRequest.getName());
        rental.setSurface(rentalRequest.getSurface());
        rental.setPrice(rentalRequest.getPrice());
        rental.setDescription(rentalRequest.getDescription());

        MultipartFile imgFile = rentalRequest.getPicture();
        String imgNameExt = UUID.randomUUID() + imageService.getExtension(imgFile.getOriginalFilename());
        String imgUrl = imageService.save(imgFile, imgNameExt);
        rental.setPicture(imgUrl);

        DBUser currentUser = userService.getCurrentUser();
        rental.setOwner_id(currentUser.getId());

        rental.setCreated_at(new Date(System.currentTimeMillis()));
        rental.setUpdated_at(new Date(System.currentTimeMillis()));

        return rentalRepository.save(rental);
    }

    public Rental updateRental(RentalRequest rentalRequest, Integer id) throws Exception {

        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("rental not found"));

        // Update only if the current user is the rental's owner
        DBUser currentUser = userService.getCurrentUser();
        if (currentUser.getId() == rental.getOwner_id()) {
            rental.setName(rentalRequest.getName());
            rental.setSurface(rentalRequest.getSurface());
            rental.setPrice(rentalRequest.getPrice());
            rental.setDescription(rentalRequest.getDescription());
            rental.setUpdated_at(new Date(System.currentTimeMillis())); 
        } else {
            throw new Exception("Rental update not authorized");
        }

        return rentalRepository.save(rental);
    }

}