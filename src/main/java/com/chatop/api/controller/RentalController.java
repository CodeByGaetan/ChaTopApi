package com.chatop.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.Rental;
import com.chatop.api.service.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Rentals", description = "API for CRUD operations on Rentals")

@RestController
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Operation(
      summary = "Get all rentals",
      description = "Get a list of all rental objects with id, name, surface, price, picture, description, owner id, created date an updated date.")
    @GetMapping("/rentals")
    public Iterable<Rental> getRentals() {
        return rentalService.getRentals();
    }

}
