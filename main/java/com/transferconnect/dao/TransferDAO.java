package com.transferconnect.dao;

import com.transferconnect.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferDAO {

	// Static map to store transfers in memory
	private static Map<String, Transfer> Transfers = new HashMap<>();

	public TransferDAO() {
		// Constructor (can be left empty)
	}



	// Récupérer tous les transferts
	public Map<String, Transfer> getAllTransfers() {
		return Transfers;
	}

	// Récupérer un transfert par son ID
	public Transfer getTransferById(String transferId) {
		return Transfers.get(transferId);
	}

	// Ajouter un nouveau transfert
	public void addTransfer(Transfer transfer) {
		if (transfer == null || transfer.getTransferId() == null) {
			throw new IllegalArgumentException("Transfer or transfer ID cannot be null");
		}
		Transfers.put(transfer.getTransferId(), transfer);
	}

	// Mettre à jour un transfert existant
	public void updateTransfer(String transferId, Transfer updatedTransfer) {
		if (transferId == null || updatedTransfer == null) {
			throw new IllegalArgumentException("Transfer ID or updated transfer cannot be null");
		}
		if (Transfers.containsKey(transferId)) {
			Transfers.put(transferId, updatedTransfer);
		}
	}

	// Supprimer un transfert
	public void deleteTransfer(String transferId) {
		if (transferId == null) {
			throw new IllegalArgumentException("Transfer ID cannot be null");
		}
		Transfers.remove(transferId);
	}

	public List<Transfer> getTransfersByUserNni(String nni) {
		if (nni == null) {
			throw new IllegalArgumentException("NNI cannot be null");
		}
		List<Transfer> userTransfers = new ArrayList<>();
		for (Transfer transfer : Transfers.values()) {
			if (transfer.getNni().equals(nni)) {
				userTransfers.add(transfer);
			}
		}
		return userTransfers;
	}
}
