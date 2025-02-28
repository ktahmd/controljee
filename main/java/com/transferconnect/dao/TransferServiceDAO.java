package com.transferconnect.dao;

import com.transferconnect.model.TransferService;
import com.transferconnect.database.InMemoryDatabase;

import java.util.Map;

public class TransferServiceDAO {

    // Récupérer tous les services de transfert
    public Map<String, TransferService> getAllTransferServices() {
        return InMemoryDatabase.transferServices;
    }

    // Récupérer un service de transfert par son ID
    public TransferService getTransferServiceById(String serviceId) {
        return InMemoryDatabase.transferServices.get(serviceId);
    }

    // Ajouter un nouveau service de transfert
    public void addTransferService(TransferService transferService) {
        InMemoryDatabase.transferServices.put(transferService.getServiceId(), transferService);
    }

    // Mettre à jour un service de transfert existant
    public void updateTransferService(String serviceId, TransferService updatedTransferService) {
        if (InMemoryDatabase.transferServices.containsKey(serviceId)) {
            InMemoryDatabase.transferServices.put(serviceId, updatedTransferService);
        }
    }

    // Supprimer un service de transfert
    public void deleteTransferService(String serviceId) {
        InMemoryDatabase.transferServices.remove(serviceId);
    }
}