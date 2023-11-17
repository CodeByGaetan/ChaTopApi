package com.chatop.api.model.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RentalRequest {
    private String name;
    private Integer surface;
    private Integer price;
    private MultipartFile picture;
    private String description;
}