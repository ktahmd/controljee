package com.transferconnect.dao;

import com.transferconnect.model.*;

import java.util.HashMap;
import java.util.Map;

public class AccountDAO {
	
	private static Map<String, Account> Accounts = new HashMap<>();
	static {
    	Accounts.put("AC1",new Account("AC1","9801370078","26303030","Bankily",12000)); //accountId, nni,Tel,bank,montant
    	Accounts.put("AC2",new Account("AC2","9801370078","26303030","Sadad",3000)); 
    	Accounts.put("AC3",new Account("AC3","9801370078","26303030","Bimbank",4000));
    	Accounts.put("AC4",new Account("AC4","1234567890","26202020","Bimbank",4000));
    	
    	
    }
	

	// Get user by constraint key (e.g., nni)
	public Map<String, Account> getAccountByConstraintKey(String nni) {
	    if (nni == null) {
	        throw new IllegalArgumentException("NNI cannot be null");
	    }
	    
	    Map<String, Account> result = new HashMap<>();
	    for (Map.Entry<String, Account> entry : Accounts.entrySet()) {
	        if (entry.getValue().getNni().equals(nni)) {
	            result.put(entry.getKey(), entry.getValue());
	        }
	    }
	    
	    return result.isEmpty() ? null : result; // Return null if no user found
	}
	public Map<String, Account> getAccountByConstraintKeytel(String tel) {
	    if (tel == null) {
	        throw new IllegalArgumentException("NNI cannot be null");
	    }
	    
	    Map<String, Account> result = new HashMap<>();
	    for (Map.Entry<String, Account> entry : Accounts.entrySet()) {
	        if (entry.getValue().getTel().equals(tel)) {
	            result.put(entry.getKey(), entry.getValue());
	        }
	    }
	    
	    return result.isEmpty() ? null : result; // Return null if no user found
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