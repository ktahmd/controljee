package com.transferconnect.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest {
    @JsonProperty
    private String nni;
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private String username;
    @JsonProperty
    private String password;
    @JsonProperty
    private String role;

    // Default constructor for Jackson
    public RegisterRequest() {
    }

    // Constructor with all fields
    public RegisterRequest(String nni, String firstName, String lastName, 
                           String username, String password, String role) {
        this.nni = nni;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public String getNni() {
        return nni;
    }

    public void setNni(String nni) {
        this.nni = nni;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}