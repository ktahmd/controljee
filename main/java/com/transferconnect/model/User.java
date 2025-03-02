package com.transferconnect.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	@JsonProperty
    private String nni; // Numéro National d'Identité
	/*@JsonProperty
    private String id;*/
	@JsonProperty
    private String firstName;
	@JsonProperty
    private String lastName;
	@JsonProperty
    private String username;
	@JsonProperty
    private String password;
	@JsonProperty
    private Role role;

    public User() {
    }

    public User(String id, String firstName, String lastName, String nni, String username, String password, Role role) {
       // this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nni = nni;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    /*public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/

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

    public String getNni() {
        return nni;
    }

    public void setNni(String nni) {
        this.nni = nni;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                //"id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nni='" + nni + '\'' +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}