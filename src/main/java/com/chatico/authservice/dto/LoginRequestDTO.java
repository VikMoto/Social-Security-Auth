package com.chatico.authservice.dto;

public class LoginRequestDTO {
    private String username;
    private String password;

    public LoginRequestDTO() {
        // Default constructor required by some frameworks (e.g., Jackson).
    }

    public LoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
