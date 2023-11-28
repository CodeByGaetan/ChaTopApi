package com.chatop.api.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chatop.api.model.entity.DBUser;
import com.chatop.api.model.entity.Rental;
import com.chatop.api.model.request.RentalAddRequest;
import com.chatop.api.model.request.RentalUpRequest;
import com.chatop.api.model.response.RentalResponse;
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

        List<RentalResponse> rentalResponseList = new ArrayList<RentalResponse>();
        rentalList.forEach(rental -> {
            rentalResponseList.add(generatRentalResponse(rental));
        });

        return new RentalsResponse(rentalResponseList);
    }

    public RentalResponse getRentalById(Integer id) {
        Rental rental = rentalRepository.findById(id).orElseThrow();
        return generatRentalResponse(rental);
    }

    public Rental createRental(RentalAddRequest rentalAddRequest) {
        Rental rental = new Rental();
        rental.setName(rentalAddRequest.getName());
        rental.setSurface(rentalAddRequest.getSurface());
        rental.setPrice(rentalAddRequest.getPrice());
        rental.setDescription(rentalAddRequest.getDescription());

        MultipartFile imgFile = rentalAddRequest.getPicture();
        String imgNameExt = UUID.randomUUID() + imageService.getExtension(imgFile.getOriginalFilename());
        String imgUrl = imageService.save(imgFile, imgNameExt);
        rental.setPicture(imgUrl);

        DBUser currentUser = userService.getCurrentUser();
        rental.setOwner_id(currentUser.getId());

        rental.setCreated_at(new Date(System.currentTimeMillis()));
        rental.setUpdated_at(new Date(System.currentTimeMillis()));

        return rentalRepository.save(rental);
    }

    public Rental updateRental(RentalUpRequest rentalUpRequest, Integer id) throws Exception {

        Rental rental = rentalRepository.findById(id).orElseThrow();

        // Update only if the current user is the rental's owner
        DBUser currentUser = userService.getCurrentUser();
        if (currentUser.getId() == rental.getOwner_id()) {
            rental.setName(rentalUpRequest.getName());
            rental.setSurface(rentalUpRequest.getSurface());
            rental.setPrice(rentalUpRequest.getPrice());
            rental.setDescription(rentalUpRequest.getDescription());
            rental.setUpdated_at(new Date(System.currentTimeMillis()));
        } else {
            throw new Exception("Rental update not authorized");
        }

        return rentalRepository.save(rental);
    }

    public RentalResponse generatRentalResponse(Rental rental) {

        RentalResponse rentalResponse = new RentalResponse();
        rentalResponse.setId(rental.getId());
        rentalResponse.setName(rental.getName());
        rentalResponse.setSurface(rental.getSurface());
        rentalResponse.setPrice(rental.getPrice());
        rentalResponse.setPicture(rental.getPicture());
        rentalResponse.setDescription(rental.getDescription());
        rentalResponse.setOwner_id(rental.getOwner_id());
        rentalResponse.setCreated_at(rental.getCreated_at());
        rentalResponse.setUpdated_at(rental.getUpdated_at());

        return rentalResponse;
    }

}