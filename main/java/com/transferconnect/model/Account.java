package com.transferconnect.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
    @JsonProperty
    private String accountId; // Identifiant unique du compte
    @JsonProperty
    private String firstName; // Prénom de l'utilisateur
    @JsonProperty
    private String lastName; // Nom de l'utilisateur
    @JsonProperty
    private String nni; // Numéro National d'Identification (NNI)
    @JsonProperty
    private String bank; // Banque associée au compte
    @JsonProperty
    private double balance; // Solde du compte
    @JsonProperty
    private String currency; // Devise du compte (ex: EUR, USD)

    // Constructeur par défaut
    public Account() {}

    // Constructeur avec paramètres
    public Account(String accountId, String firstName, String lastName, String nni, String bank, double balance, String currency) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nni = nni;
        this.bank = bank;
        this.balance = balance;
        this.currency = currency;
    }

    // Getters et Setters
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getNni() { return nni; }
    public void setNni(String nni) { this.nni = nni; }

    public String getBank() { return bank; }
    public void setBank(String bank) { this.bank = bank; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}