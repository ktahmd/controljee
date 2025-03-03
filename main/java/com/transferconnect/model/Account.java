package com.transferconnect.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
    @JsonProperty
    private String accountId; // Identifiant unique du compte
    @JsonProperty
    private String nni; // Numéro National d'Identification (NNI)
    @JsonProperty
    private String bank; // Banque associée au compte
    @JsonProperty
    private double balance; // Solde du compte

    // Constructeur par défaut
    public Account() {}

    // Constructeur avec paramètres
    public Account(String accountId,  String nni, String bank, double balance) {
        this.accountId = accountId;
        this.nni = nni;
        this.bank = bank;
        this.balance = balance;
    }

    // Getters et Setters
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getNni() { return nni; }
    public void setNni(String nni) { this.nni = nni; }

    public String getBank() { return bank; }
    public void setBank(String bank) { this.bank = bank; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}