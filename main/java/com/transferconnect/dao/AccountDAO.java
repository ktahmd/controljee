package com.transferconnect.dao;

import com.transferconnect.model.*;

import java.util.HashMap;
import java.util.Map;

public class AccountDAO {
	
	private static Map<String, Account> Accounts = new HashMap<>();
  
	static {
    	Accounts.put("80029485392",new Account("80029485392","9801370078","Bankily",12000)); 
    	Accounts.put("80053692732",new Account("80053692732","9801370078","Sadad",3000)); 
    	Accounts.put("90337388484",new Account("90337388484","9801370078","Bimbank",4000)); 
    	
    }

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