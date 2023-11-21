package com.chatop.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.Rental;
import com.chatop.api.model.request.RentalRequest;
import com.chatop.api.model.response.MessageResponse;
import com.chatop.api.model.response.RentalsResponse;
import com.chatop.api.service.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "Rentals", description = "API for CRUD operations on Rentals")
@RestController
public class RentalController {

  @Autowired
  private RentalService rentalService;

  @Operation(summary = "Get all rentals",
  description = "Get a list of all rental objects with id, name, surface, price, picture, description, owner id, created date an updated date.")
  @GetMapping("/rentals")
  public RentalsResponse getRentals() {
    return rentalService.getRentals();
  }
  
	@GetMapping("/rentals/{id}")
	public Rental getRental(@PathVariable("id") final Integer id) {
		Optional<Rental> rental = rentalService.getRental(id);
		if(rental.isPresent()) {
			return rental.get();
		} else {
			return null;
		}
	}

  @PostMapping(path = "/rentals", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public MessageResponse createRental(@ModelAttribute RentalRequest rentalRequest ) {
    rentalService.createRental(rentalRequest);
    return new MessageResponse("Rental created !");
  }

  @PutMapping("/rentals/{id}")
  public MessageResponse updateRental( RentalRequest rentalRequest, @PathVariable("id") final Integer id) {
    rentalService.updateRental(rentalRequest, id);
    return new MessageResponse("Rental updated !");
  }
}
