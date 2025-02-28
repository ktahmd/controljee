package com.transferconnect.dao;

import com.transferconnect.model.Transfer;
import com.transferconnect.database.InMemoryDatabase;

import java.util.Map;

public class TransferDAO {

    // Récupérer tous les transferts
    public Map<String, Transfer> getAllTransfers() {
        return InMemoryDatabase.transfers;
    }

    // Récupérer un transfert par son ID
    public Transfer getTransferById(String transferId) {
        return InMemoryDatabase.transfers.get(transferId);
    }

    // Ajouter un nouveau transfert
    public void addTransfer(Transfer transfer) {
        InMemoryDatabase.transfers.put(transfer.getTransferId(), transfer);
    }

    // Mettre à jour un transfert existant
    public void updateTransfer(String transferId, Transfer updatedTransfer) {
        if (InMemoryDatabase.transfers.containsKey(transferId)) {
            InMemoryDatabase.transfers.put(transferId, updatedTransfer);
        }
    }

    // Supprimer un transfert
    public void deleteTransfer(String transferId) {
        InMemoryDatabase.transfers.remove(transferId);
    }
}