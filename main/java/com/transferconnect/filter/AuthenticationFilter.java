package com.transferconnect.filter;

import com.transferconnect.model.User;
import com.transferconnect.service.UserService;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/api/*"})
public class AuthenticationFilter implements Filter {

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
        
        // Vérification de l'authentification
        HttpSession session = httpRequest.getSession(false);
        
        if (session == null || session.getAttribute("userId") == null) {
            // L'utilisateur n'est pas authentifié
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Authentication required");
            return;
        }
        
        // Vérification que l'utilisateur existe toujours dans le système
        String userId = (String) session.getAttribute("userId");
        User user = UserService.getInstance().getUserById(userId);
        
        if (user == null) {
            // L'utilisateur n'existe plus dans le système
            session.invalidate();
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Invalid session");
            return;
        }
        
        // Ajout des informations de l'utilisateur à la requête pour les servlets suivants
        request.setAttribute("user", user);
        
        // Continuer le traitement de la requête
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Nettoyage des ressources
    }
    
    private boolean isPublicResource(String uri) {
        // Liste des URLs qui ne nécessitent pas d'authentification
        return uri.endsWith("/login") || 
               uri.endsWith("/register") || 
               uri.contains("/public/");
    }
}
