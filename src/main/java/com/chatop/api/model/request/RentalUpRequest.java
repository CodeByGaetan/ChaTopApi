package com.chatop.api.model.request;

import lombok.Data;

@Data
public class RentalUpRequest {
    private String name;
    private Integer surface;
    private Integer price;
    private String description;
}