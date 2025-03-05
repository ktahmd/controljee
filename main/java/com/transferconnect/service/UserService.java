package com.transferconnect.service;

import com.transferconnect.dao.*;
import com.transferconnect.model.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public class UserService {
    private UserDAO userDAO = new UserDAO();
    private AccountDAO accountDAO = new AccountDAO();
    private TransferDAO transferDAO = new TransferDAO();
    
    private static UserService instance;

    // Utilisation du pattern Singleton pour garantir une seule instance
    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    // Récupérer un utilisateur par son ID
    public User getUserById(String userId) {
        return userDAO.getUserById(userId);
    }
    
    // Récupérer un utilisateur par son nom d'utilisateur
    public User getUserByUsername(String username) {
        return userDAO.getUserByConstraintKey(username);
    }

    // Vérifier la validité des identifiants d'un utilisateur
    public boolean validateUser(String username, String password) {
        User user = userDAO.getUserByConstraintKey(username);
        return user != null && user.getPassword().equals(password);
    }

    // Changer le mot de passe d'un utilisateur
    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        User user = userDAO.getUserById(userId);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userDAO.updateUser(userId, user);
            return true;
        }
        return false;
    }

    // Réinitialiser le mot de passe d'un utilisateur
    public boolean resetPassword(String userId, String newPassword) {
        User user = userDAO.getUserById(userId);
        if (user != null) {
            user.setPassword(newPassword);
            userDAO.updateUser(userId, user);
            return true;
        }
        return false;
    }

    // Vérifier si un utilisateur existe
    public boolean isUserExists(String userId) {
        return userDAO.getUserById(userId) != null;
    }

    // Récupérer un utilisateur à partir de la session
    public User getUserBySessionUsername(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            return userDAO.getUserByConstraintKey(username);
        }
        return null;
    }

    // Récupérer tous les comptes d'un utilisateur à partir de la session
    public Map<String, Account> getAllAccountsBySessionUser(HttpSession session) {
        User user = getUserBySessionUsername(session);
        if (user != null) {
            return accountDAO.getAccountByConstraintKey(user.getNni());
        }
        return null;
    }
    
    // Récupérer toutes les transactions d'un utilisateur à partir de la session
    public List<Transfer> getAllTransfersBySessionUser(HttpSession session) {
        User user = getUserBySessionUsername(session);
        if (user != null) {
            return transferDAO.getTransfersByUserNni(user.getNni());
        }
        return null;
    }
}