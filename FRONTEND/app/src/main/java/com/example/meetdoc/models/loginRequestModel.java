package com.example.meetdoc.models;

import com.google.gson.annotations.SerializedName;

public class loginRequestModel {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("role")
    private String role;

    // Constructor
    public loginRequestModel(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
