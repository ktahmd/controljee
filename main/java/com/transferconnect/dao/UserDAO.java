package com.transferconnect.dao;

import com.transferconnect.model.Account;
import com.transferconnect.database.InMemoryDatabase;

import java.util.Map;

public class AccountDAO {

    // Récupérer tous les comptes
    public Map<String, Account> getAllAccounts() {
        return InMemoryDatabase.accounts;
    }

    // Récupérer un compte par son ID
    public Account getAccountById(String accountId) {
        return InMemoryDatabase.accounts.get(accountId);
    }

    // Ajouter un nouveau compte
    public void addAccount(Account account) {
        InMemoryDatabase.accounts.put(account.getAccountId(), account);
    }

    // Mettre à jour un compte existant
    public void updateAccount(String accountId, Account updatedAccount) {
        if (InMemoryDatabase.accounts.containsKey(accountId)) {
            InMemoryDatabase.accounts.put(accountId, updatedAccount);
        }
    }

    // Supprimer un compte
    public void deleteAccount(String accountId) {
        InMemoryDatabase.accounts.remove(accountId);
    }
}