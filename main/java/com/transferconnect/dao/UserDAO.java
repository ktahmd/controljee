package com.transferconnect.dao;

import com.transferconnect.model.*;
import java.util.HashMap;
import java.util.Map;

public class UserDAO {

    // Static map pour stocker les utilisateurs en mémoire
    private static Map<String, User> Users = new HashMap<>();

    public UserDAO() {
        // Constructeur (peut être laissé vide)
    }

    // Récupérer un utilisateur par son ID
    public User getUserById(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return Users.get(userId);
    }

    // Ajouter un nouvel utilisateur
    public void addUser(User user) {
        if (user == null || user.getNni() == null) {
            throw new IllegalArgumentException("User or user ID cannot be null");
        }
        Users.put(user.getNni(), user);
    }

    // Mettre à jour un utilisateur existant
    public void updateUser(String userId, User updatedUser) {
        if (userId == null || updatedUser == null) {
            throw new IllegalArgumentException("User ID or updated user cannot be null");
        }
        if (Users.containsKey(userId)) {
            Users.put(userId, updatedUser);
        }
    }

    // Supprimer un utilisateur
    public void deleteUser(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        Users.remove(userId);
    }
}