package com.chatop.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.request.RentalAddRequest;
import com.chatop.api.model.request.RentalUpRequest;
import com.chatop.api.model.response.MessageResponse;
import com.chatop.api.model.response.RentalResponse;
import com.chatop.api.model.response.RentalsResponse;
import com.chatop.api.service.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Rentals", description = "API for CRUD operations on Rentals")
@RestController
public class RentalController {

  @Autowired
  private RentalService rentalService;

  @Operation(summary = "Get all rentals")
  @GetMapping("/rentals")
  public RentalsResponse getRentals() {
    return rentalService.getRentals();
  }

  @Operation(summary = "Get a rental by Id")
  @GetMapping("/rentals/{id}")
  public RentalResponse getRentalById(@PathVariable("id") final Integer id) {
    return rentalService.getRentalById(id);
  }

  @Operation(summary = "Create a new rental")
  @PostMapping(path = "/rentals", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public MessageResponse createRental(@ModelAttribute RentalAddRequest rentalAddRequest) {
    rentalService.createRental(rentalAddRequest);
    return new MessageResponse("Rental created !");
  }

  @Operation(summary = "Update a rental")
  @PutMapping("/rentals/{id}")
  public MessageResponse updateRental(RentalUpRequest rentalUpRequest, @PathVariable("id") final Integer id) {
    try {
      rentalService.updateRental(rentalUpRequest, id);
    } catch (Exception e) {
      // Rental update not authorized
    }
    return new MessageResponse("Rental updated !");
  }
}
