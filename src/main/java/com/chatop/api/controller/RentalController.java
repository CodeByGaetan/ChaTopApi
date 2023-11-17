package com.chatop.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.Rental;
import com.chatop.api.model.request.RentalRequest;
import com.chatop.api.model.response.MessageResponse;
import com.chatop.api.service.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Rentals", description = "API for CRUD operations on Rentals")
@RestController
public class RentalController {

  @Autowired
  private RentalService rentalService;

  @Operation(summary = "Get all rentals",
  description = "Get a list of all rental objects with id, name, surface, price, picture, description, owner id, created date an updated date.")
  @GetMapping("/rentals")
  public Iterable<Rental> getRentals() {
    return rentalService.getRentals();
  }

  @PostMapping(path = "/rentals", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<MessageResponse> createRental(@ModelAttribute RentalRequest rentalRequest ) {
    // var test = rentalRequest.getPicture().getResource().getURL().toString();
    rentalService.createRental(rentalRequest);
    return ResponseEntity.ok(new MessageResponse("Rental created !"));
  }

}
