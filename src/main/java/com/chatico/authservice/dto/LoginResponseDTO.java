package com.chatico.authservice.dto;

import org.springframework.stereotype.Component;

@Component
public class LoginResponseDTO {
    private String jwt;
    private String username;

    public LoginResponseDTO() {
        // Default constructor required by some frameworks (e.g., Jackson).
    }

    public LoginResponseDTO(String jwt, String username) {
        this.jwt = jwt;
        this.username = username;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
