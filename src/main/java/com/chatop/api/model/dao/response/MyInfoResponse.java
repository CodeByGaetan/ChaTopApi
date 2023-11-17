package com.chatop.api.model.dao.response;

import java.sql.Date;

import lombok.Data;

@Data
public class MyInfoResponse {
    private Integer ID;
    private String email;
    private String name;    
    private Date created_at;
    private Date updated_at;
}