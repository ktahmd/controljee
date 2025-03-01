package com.transferconnect.service;

import com.transferconnect.dao.TransferDAO;
import com.transferconnect.model.Transfer;
import java.util.Map;

public class TransferService {

    private final TransferDAO transferDAO;

    // Constructeur pour injecter le DAO
    public TransferService(TransferDAO transferDAO) {
        this.transferDAO = transferDAO;
    }

    // Récupérer tous les transferts
    public Map<String, Transfer> getAllTransfers() {
        return transferDAO.getAllTransfers();
    }

    // Récupérer un transfert par son ID
    public Transfer getTransferById(String transferId) {
        if (transferId == null) {
            throw new IllegalArgumentException("Transfer ID cannot be null");
        }
        return transferDAO.getTransferById(transferId);
    }

    // Créer un nouveau transfert
    public void createTransfer(Transfer transfer) {
        if (transfer == null || transfer.getTransferId() == null) {
            throw new IllegalArgumentException("Transfer or transfer ID cannot be null");
        }
        transferDAO.addTransfer(transfer);
    }

    // Mettre à jour un transfert existant
    public void updateTransfer(String transferId, Transfer updatedTransfer) {
        if (transferId == null || updatedTransfer == null) {
            throw new IllegalArgumentException("Transfer ID or updated transfer cannot be null");
        }
        transferDAO.updateTransfer(transferId, updatedTransfer);
    }

    // Supprimer un transfert
    public void deleteTransfer(String transferId) {
        if (transferId == null) {
            throw new IllegalArgumentException("Transfer ID cannot be null");
        }
        transferDAO.deleteTransfer(transferId);
    }
}