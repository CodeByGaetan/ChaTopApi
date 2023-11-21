package com.chatop.api.model.response;

import com.chatop.api.model.Rental;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RentalsResponse {
    private Iterable<Rental> rentals;
}