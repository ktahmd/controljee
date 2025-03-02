package com.transferconnect.dao;

import com.transferconnect.model.TransferService;
import java.util.HashMap;
import java.util.Map;

public class TransferServiceDAO {

    // Static map pour stocker les services de transfert en mémoire
    private static Map<String, TransferService> transferServices = new HashMap<>();

    public TransferServiceDAO() {
        // Constructeur vide
    }

    // Récupérer un service de transfert par son ID
    public TransferService getTransferServiceById(String serviceId) {
        if (serviceId == null) {
            throw new IllegalArgumentException("Service ID cannot be null");
        }
        return transferServices.get(serviceId);
    }

    // Ajouter un nouveau service de transfert
    public void addTransferService(TransferService service) {
        if (service == null || service.getServiceId() == null) {
            throw new IllegalArgumentException("Service or service ID cannot be null");
        }
        transferServices.put(service.getServiceId(), service);
    }

    // Mettre à jour un service de transfert existant
    public void updateTransferService(String serviceId, TransferService updatedService) {
        if (serviceId == null || updatedService == null) {
            throw new IllegalArgumentException("Service ID or updated service cannot be null");
        }
        if (transferServices.containsKey(serviceId)) {
            transferServices.put(serviceId, updatedService);
        }
    }

    // Supprimer un service de transfert
    public void deleteTransferService(String serviceId) {
        if (serviceId == null) {
            throw new IllegalArgumentException("Service ID cannot be null");
        }
        transferServices.remove(serviceId);
    }
}
