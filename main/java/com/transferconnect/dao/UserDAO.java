package com.transferconnect.dao;

import com.transferconnect.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDAO {

    // Static map pour stocker les utilisateurs en mémoire
    private static Map<String, User> Users = new HashMap<>();

    static {
    	    Users.put("0000000001",new User("0000000001","system", "admin",   "admin", "admin", Role.ADMIN));
            Users.put("9801370078",new User("9801370078","Ahmed","medvall","ahmed23", "1234",Role.USER));  
            Users.put("1234567890",new User("1234567890","fatou","saw","ftsaw", "1234",Role.USER)); 
    }

    // Récupérer un utilisateur par son ID
    public User getUserById(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return Users.get(userId);
    }
    // Get user by constraint key (e.g., username)
    public User getUserByConstraintKey(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        for (User user : Users.values()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // Return null if no user found
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

 // Récupérer les utilisateurs par rôle
    public List<User> getUsersByRole(Role role) {
        return Users.values().stream()
                .filter(user -> user.getRole() == role)
                .collect(Collectors.toList());
    }

 // Compter le nombre total d'utilisateurs
    public int countUsers() {
        return Users.size();
    }
    
 // Compter les utilisateurs par rôle
    public int countUsersByRole(Role role) {
        return (int) Users.values().stream()
                .filter(user -> user.getRole() == role)
                .count();
    }
    
 // Ajouter cette méthode pour récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return new ArrayList<>(Users.values());
    }

}