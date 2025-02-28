package com.transferconnect.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty
    private String id;
    
    @JsonProperty
    private String name;
    
    @JsonProperty
    private String passwd;

    // Constructeur par défaut
    public User() {}

    // Constructeur avec paramètres
    public User(String id, String name, String passwd) {
        this.id = id;
        this.name = name;
        this.passwd = passwd;
    }

    // Getter pour l'attribut id
    public String getId() {
        return id;
    }

    // Setter pour l'attribut id
    public void setId(String id) {
        this.id = id;
    }

    // Getter pour l'attribut name
    public String getName() {
        return name;
    }

    // Setter pour l'attribut name
    public void setName(String name) {
        this.name = name;
    }

    // Getter pour l'attribut passwd
    public String getPasswd() {
        return passwd;
    }

    // Setter pour l'attribut passwd
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}