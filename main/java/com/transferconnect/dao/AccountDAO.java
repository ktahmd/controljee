package com.transferconnect.dao;

import com.transferconnect.model.*;

import java.util.HashMap;
import java.util.Map;

public class AccountDAO {
	
	private static Map<String, Account> Accounts = new HashMap<>();
  
    public AccountDAO() {   }

    // Récupérer un compte par son ID
    public Account getAccountById(String accountId) {
        return Accounts.get(accountId);
    }

    // Ajouter un nouveau compte
    public void addAccount(Account account) {
    	Accounts.put(account.getAccountId(), account);
    }

    // Mettre à jour un compte existant
    public void updateAccount(String accountId, Account updatedAccount) {
        if (Accounts.containsKey(accountId)) {
        	Accounts.put(accountId, updatedAccount);
        }
    }

    // Supprimer un compte
    public void deleteAccount(String accountId) {
    	Accounts.remove(accountId);
    }
}