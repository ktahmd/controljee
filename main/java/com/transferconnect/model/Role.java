package com.transferconnect.model;

public class Role {
    private String roleId; // Identifiant du rôle
    private String roleName; // Nom du rôle (ex: ADMIN, RESPONSIBLE, USER)

    // Constructeur par défaut
    public Role() {}

    // Constructeur avec paramètres
    public Role(String roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    // Getters et Setters
    public String getRoleId() { return roleId; }
    public void setRoleId(String roleId) { this.roleId = roleId; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}