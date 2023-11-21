package com.chatop.api.repository;

import org.springframework.stereotype.Repository;

import com.chatop.api.model.entity.Rental;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Integer> {
    
}