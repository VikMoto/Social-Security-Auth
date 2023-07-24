package com.chatico.authservice.domain;

import lombok.Data;

@Data
public class GoogleUserInfo {
    private String id;
    private String email;
    private String name;
}
