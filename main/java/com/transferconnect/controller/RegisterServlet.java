package com.transferconnect.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.transferconnect.model.User;
import com.transferconnect.model.Role;
import com.transferconnect.model.RegisterRequest;
import com.transferconnect.service.UserService;
import com.transferconnect.dao.UserDAO;

@Path("/register")
public class RegisterServlet {
    private final UserService userService = UserService.getInstance();
    private final UserDAO userDAO = new UserDAO();

    /*@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(RegisterRequest request) {
        // Check if username already exists
        if (userDAO.getUserByConstraintKey(request.getUsername()) != null) {
            return Response.status(Response.Status.CONFLICT)
                          .entity("{\"message\":\"Username already exists\",\"success\":false}")
                          .build();
        }*/
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(RegisterRequest request) {
        // Validation des données
        if (request.getNni() == null || request.getFirstName() == null || 
            request.getLastName() == null || request.getUsername() == null || 
            request.getPassword() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                         .entity("{\"message\":\"All fields are required\",\"success\":false}")
                         .build();
        }
        
        // Check if NNI already exists
        if (userService.isUserExists(request.getNni())) {
            return Response.status(Response.Status.CONFLICT)
                          .entity("{\"message\":\"NNI already exists\",\"success\":false}")
                          .build();
        }
        
        // Create new user object from request
        User newUser = new User(
            request.getNni(),
            request.getFirstName(),
            request.getLastName(),
            request.getUsername(),
            request.getPassword(),
            "USER".equals(request.getRole()) ? Role.USER : Role.ADMIN
        );
        
        // Add the new user
        try {
            userDAO.addUser(newUser);
            return Response.ok("{\"message\":\"Registration Successful\",\"success\":true}")
                          .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                          .entity("{\"message\":\"Registration Failed: " + e.getMessage() + "\",\"success\":false}")
                          .build();
        }
    }
}