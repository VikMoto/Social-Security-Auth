package com.chatico.authservice.dto;

import lombok.Data;

@Data
public class FacebookUserInfo {
    private String id;
    private String email;
    private String name;
}
