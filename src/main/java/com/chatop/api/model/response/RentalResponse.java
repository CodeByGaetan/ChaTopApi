package com.chatop.api.model.response;

import java.sql.Date;

import lombok.Data;

@Data
public class RentalResponse {
    private Integer id;
    private String name;
    private Integer surface;
    private Integer price;
    private String picture;
    private String description;
    private Integer owner_id;
    private Date created_at;
    private Date updated_at;
}
