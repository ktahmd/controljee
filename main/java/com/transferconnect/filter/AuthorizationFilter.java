package com.transferconnect.filter;

import com.transferconnect.dao.UserDAO;
import com.transferconnect.model.Role;
import com.transferconnect.model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class AuthorizationFilter implements Filter {

    private UserDAO userDAO; // Declare UserDAO

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialize UserDAO
        userDAO = new UserDAO();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get the session (do not create a new session if it doesn't exist)
        HttpSession session = httpRequest.getSession(false);

        // If there's no session, do nothing (allow the request to proceed)
        if (session == null) {
            chain.doFilter(request, response);
            return;
        }

        // Get the username from the session
        String username = (String) session.getAttribute("username");

        // If the username is not in the session, do nothing (allow the request to proceed)
        if (username == null) {
            chain.doFilter(request, response);
            return;
        }

        // Fetch the user from the UserDAO based on the username
        User user = userDAO.getUserByConstraintKey(username);

        // If the user is not found in the database (shouldn't happen if authentication works properly)
        if (user == null) {
            chain.doFilter(request, response); // Just pass the request along
            return;
        }

        // Get the request URI
        String requestURI = httpRequest.getRequestURI();

        // Check if the requested URL is "/admin-dashboard.html"
        if (requestURI.equals("/Controljee/admin-dashboard.html")) {
            // If the user is not an Admin, deny access (403 Forbidden)
            if (user.getRole() != Role.ADMIN) {
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResponse.getWriter().write("Access forbidden: insufficient privileges");
                return;
            }
        }

        // If the requested URL is any other URL (excluding "/admin-dashboard.html"), allow access
        chain.doFilter(request, response); // Continue processing the request
    }

    @Override
    public void destroy() {
        // Clean up if needed
    }
}
