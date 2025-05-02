package com.example.cinemaapp.dto.request;


public class SignUpRequestDTO {
    private String fullName;
    private String password;
    private String confirmPassword;
    private String email;

    public SignUpRequestDTO(String fullName, String password, String confirmPassword, String email) {
        this.fullName = fullName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
