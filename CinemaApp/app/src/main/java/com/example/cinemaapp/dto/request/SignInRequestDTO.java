package com.example.cinemaapp.dto.request;

public class SignInRequestDTO {

    private String email;
    private String password;

    public SignInRequestDTO(String username, String password) {
        this.email = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
