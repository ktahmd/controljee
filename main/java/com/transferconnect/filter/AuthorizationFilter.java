package com.transferconnect.filter;

import com.transferconnect.model.Role;
import com.transferconnect.model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@WebFilter(urlPatterns = {"/api/*"})
public class AuthorizationFilter implements Filter {
    
    // Définition des droits d'accès par URL et rôle
    private Map<String, Set<Role>> urlAccessRights;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisation des droits d'accès
        urlAccessRights = new HashMap<>();	
        
        // Configuration des droits d'accès pour les différentes URLs
        
        // URLs pour l'administrateur
        addUrlAccessRight("/api/admin/*", Role.ADMIN);
        addUrlAccessRight("/api/service/create", Role.ADMIN);
        addUrlAccessRight("/api/user/role", Role.ADMIN);
        
        // URLs pour les responsables d'agence
        addUrlAccessRight("/api/agency/*", Role.AGENCY_MANAGER, Role.ADMIN);
        addUrlAccessRight("/api/transfer/cancel", Role.AGENCY_MANAGER, Role.ADMIN);
        
        // URLs pour tous les utilisateurs authentifiés
        addUrlAccessRight("/api/account/*", Role.USER, Role.AGENCY_MANAGER, Role.ADMIN);
        addUrlAccessRight("/api/transfer/create", Role.USER, Role.AGENCY_MANAGER, Role.ADMIN);
        addUrlAccessRight("/api/user/profile", Role.USER, Role.AGENCY_MANAGER, Role.ADMIN);
        addUrlAccessRight("/api/user/password", Role.USER, Role.AGENCY_MANAGER, Role.ADMIN);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Récupération de l'utilisateur depuis l'AttributeRequest (ajouté par AuthenticationFilter)
        User user = (User) request.getAttribute("user");
        
        // Si l'utilisateur n'est pas défini, c'est que l'AuthenticationFilter a laissé passer la requête
        // sans authentification, donc on continue simplement
        if (user == null) {
            chain.doFilter(request, response);
            return;
        }
        
        // Vérification des droits d'accès
        String requestURI = httpRequest.getRequestURI();
        if (!hasAccess(requestURI, user.getRole())) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.getWriter().write("Access forbidden: insufficient privileges");
            return;
        }
        
        // Continuer le traitement de la requête
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Nettoyage des ressources
    }
    
    private void addUrlAccessRight(String urlPattern, Role... roles) {
        Set<Role> allowedRoles = urlAccessRights.computeIfAbsent(urlPattern, k -> new HashSet<>());
        for (Role role : roles) {
            allowedRoles.add(role);
        }
    }
    
    private boolean hasAccess(String requestURI, Role userRole) {
        // Parcourir toutes les règles d'accès définies
        for (Map.Entry<String, Set<Role>> entry : urlAccessRights.entrySet()) {
            String urlPattern = entry.getKey();
            Set<Role> allowedRoles = entry.getValue();
            
            // Vérifier si l'URL de la requête correspond au pattern
            if (matchesUrlPattern(requestURI, urlPattern)) {
                // Vérifier si le rôle de l'utilisateur est autorisé
                return allowedRoles.contains(userRole);
            }
        }
        
        // Par défaut, si aucune règle ne correspond, on autorise l'accès
        // (c'est l'AuthenticationFilter qui s'occupe de vérifier que l'utilisateur est authentifié)
        return true;
    }
    
    private boolean matchesUrlPattern(String requestURI, String urlPattern) {
        // Logique simplifiée de correspondance des patterns d'URL
        if (urlPattern.endsWith("/*")) {
            String prefix = urlPattern.substring(0, urlPattern.length() - 2);
            return requestURI.startsWith(prefix);
        } else {
            return requestURI.equals(urlPattern);
        }
    }
}