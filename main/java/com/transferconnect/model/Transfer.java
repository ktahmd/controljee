package com.transferconnect.model;

import java.util.Date;

public class Transfer {
    private String transferId; // Identifiant unique du transfert
    private String nni;
    private String senderAccountId; // Compte source
    private String receiverAccountId; // Compte destinataire
    private String bankservice; 
    private String versbankservice;
    private double amount; // Montant transféré
    private Date transferDate; // Date du transfert
    private String status; // Statut du transfert (ex: COMPLETED, PENDING, CANCELLED)

    // Constructeur par défaut
    public Transfer() {}

    // Constructeur avec paramètres
    public Transfer(String transferId, String nni, String senderAccountId, String receiverAccountId, 
            String bankservice, String versbankservice, double amount, Date transferDate, String status) {
this.transferId = transferId;
this.nni = nni;
this.senderAccountId = senderAccountId;
this.receiverAccountId = receiverAccountId;
this.bankservice = bankservice;
this.versbankservice = versbankservice;
this.amount = amount;
this.transferDate = transferDate;
this.status = status;
}


    // Getters et Setters
    public String getTransferId() { return transferId; }
    public void setTransferId(String transferId) { this.transferId = transferId; }

    public String getSenderAccountId() { return senderAccountId; }
    public void setSenderAccountId(String senderAccountId) { this.senderAccountId = senderAccountId; }

    public String getReceiverAccountId() { return receiverAccountId; }
    public void setReceiverAccountId(String receiverAccountId) { this.receiverAccountId = receiverAccountId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Date getTransferDate() { return transferDate; }
    public void setTransferDate(Date transferDate) { this.transferDate = transferDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getBankName() { return bankservice; }
    public void setBankName(String bankservice) { this.bankservice = bankservice; }
    public String getNni() {
		return nni;
	}
    public void setNni(String nni) {
		this.nni = nni;
	}
    public String getVersbankservice() {
		return versbankservice;
	}
    public void setVersbankservice(String versbankservice) {
		this.versbankservice = versbankservice;
	}
}