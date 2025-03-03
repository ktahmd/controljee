package com.transferconnect.filter;

import com.transferconnect.model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebFilter(urlPatterns = {"/*"})
public class LoggingFilter implements Filter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisation du filtre
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        LocalDateTime timestamp = LocalDateTime.now();
        
        // Récupération des informations de l'utilisateur
        User user = (User) request.getAttribute("user");
        String username = "anonymous";
        
        if (user != null) {
            username = user.getUsername();
        } else {
            HttpSession session = httpRequest.getSession(false);
            if (session != null && session.getAttribute("userId") != null) {
                username = "user_" + session.getAttribute("userId");
            }
        }
        
        // Logging des informations de la requête
        String logMessage = String.format(
            "[%s] %s - %s %s (IP: %s)",
            timestamp.format(formatter),
            username,
            httpRequest.getMethod(),
            httpRequest.getRequestURI(),
            request.getRemoteAddr()
        );
        
        System.out.println(logMessage);
        
        // Mesure du temps d'exécution
        long startTime = System.currentTimeMillis();
        
        // Continuer le traitement de la requête
        chain.doFilter(request, response);
        
        // Calculer et logger le temps d'exécution
        long endTime = System.currentTimeMillis();
        System.out.println(String.format(
            "[%s] Request completed in %d ms",
            timestamp.format(formatter),
            (endTime - startTime)
        ));
    }

    @Override
    public void destroy() {
        // Nettoyage des ressources
    }
}