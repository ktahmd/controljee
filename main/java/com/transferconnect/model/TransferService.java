package com.transferconnect.model;

public class TransferService {
    private String serviceId; // Identifiant unique du service
    private String serviceName; // Nom du service (ex: Bankily, Masrivi, Ghaza, Marsoul)
    private String responsibleId; // Identifiant du responsable du service

    // Constructeur par défaut
    public TransferService() {}

    // Constructeur avec paramètres
    public TransferService(String serviceId, String serviceName, String responsibleId) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.responsibleId = responsibleId;
    }

    // Getters et Setters
    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getResponsibleId() { return responsibleId; }
    public void setResponsibleId(String responsibleId) { this.responsibleId = responsibleId; }
}