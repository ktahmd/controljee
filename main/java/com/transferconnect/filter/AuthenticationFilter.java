package com.transferconnect.filter;

import com.transferconnect.model.User;
import com.transferconnect.service.UserService;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
 // Appliquer le filtre à toutes les requêtes sous /api/*
public class AuthenticationFilter implements Filter {

    private final UserService userService = new UserService(); // Création directe d'une instance de UserService

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisation du filtre
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        
        // URLs publiques qui ne nécessitent pas d'authentification
        String requestURI = httpRequest.getRequestURI();
        if (isPublicResource(requestURI)) {
            chain.doFilter(request, response);
            return;
        }
        
        // Récupérer la session de l'utilisateur (sans créer une nouvelle session)
        HttpSession session = httpRequest.getSession(false);
        
        if (session == null || session.getAttribute("username") == null) {
            httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            httpResponse.setHeader("Pragma", "no-cache");
            httpResponse.setHeader("Expires", "0");
            // Si l'utilisateur n'est pas connecté, renvoyer une erreur 401 (Unauthorized)
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("{\"message\":\"Authentification requise\"}");
            System.out.println("🚨 Unauthorized request to: " + httpRequest.getRequestURI());

            return;
        }
        
        // Récupérer les informations de l'utilisateur depuis UserService
        String username = (String) session.getAttribute("username");
        User user = userService.getUserByUsername(username);
        
        if (user == null) {
            // Si l'utilisateur n'existe plus dans la base de données, invalider la session
            session.invalidate();
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("{\"message\":\"Session invalide\"}");
            System.out.println("🚨 Unauthorized request to: " + httpRequest.getRequestURI());
            return;
        }
        
        // Ajouter l'utilisateur dans l'objet requête pour les prochains traitements
        request.setAttribute("user", user);
        
        // Continuer le traitement de la requête
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Nettoyage des ressources
    }
    
    /**
     * Vérifie si l'URI correspond à une ressource publique ne nécessitant pas d'authentification.
     * @param uri L'URI de la requête.
     * @return true si l'URI est publique, sinon false.
     */
    private boolean isPublicResource(String uri) {
    	// Liste des URLs qui ne nécessitent pas d'authentification
    	System.out.println("🔍 Checking URI: " + uri);
        
        boolean isPublic =  uri.contains("/login.html") 
        		|| uri.contains("/register.html") 
                || uri.contains("/public/") 
                || uri.contains("/api/login")
                || uri.contains("/api/register")
                || uri.contains("/api/logout")
                || uri.contains("/assets/js");

        System.out.println("🛑 Is public resource? " + isPublic);
        return isPublic;
        
    }
}
