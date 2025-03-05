package com.transferconnect.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.ArrayList;

public class Service {
    @JsonProperty
    private String id;
    
    @JsonProperty
    private String name;
    
    @JsonProperty
    private boolean active;
    
    @JsonProperty
    private List<String> managerNnis; // Liste des NNI des responsables d'agence assignés
    
    public Service() {
        this.managerNnis = new ArrayList<>();
    }
    
    public Service(String id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.managerNnis = new ArrayList<>();
    }
 // Dans la classe Service.java
    private List<String> managerIds = new ArrayList<>();

    public void assignManager(String managerNni) {
        if (!managerIds.contains(managerNni)) {
            managerIds.add(managerNni);
        }
    }

    public void removeManager(String managerNni) {
        managerIds.remove(managerNni);
    }

    public boolean hasManager(String managerNni) {
        return managerIds.contains(managerNni);
    }

    public List<String> getManagerIds() {
        return new ArrayList<>(managerIds);
    }
    // Getters et Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public List<String> getManagerNnis() {
        return managerNnis;
    }
    
    public void setManagerNnis(List<String> managerNnis) {
        this.managerNnis = managerNnis;
    }
    
    /*public void assignManager(String managerNni) {
        if (!managerNnis.contains(managerNni)) {
            managerNnis.add(managerNni);
        }
    }
    
    public void removeManager(String managerNni) {
        managerNnis.remove(managerNni);
    }
    
    public boolean hasManager(String managerNni) {
        return managerNnis.contains(managerNni);
    }*/
    
    @Override
    public String toString() {
        return "Service{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", managerCount=" + managerNnis.size() +
                '}';
    }
}