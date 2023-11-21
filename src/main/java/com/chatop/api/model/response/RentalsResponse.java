package com.chatop.api.model.response;

import com.chatop.api.model.entity.Rental;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentalsResponse {
    public Iterable<Rental> rentals;
}