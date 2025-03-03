package com.transferconnect.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.transferconnect.model.LoginRequest;
import com.transferconnect.service.UserService;



@Path("/login")
public class LoginServlet {
	private final UserService userService = new UserService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) {
        boolean isValid = userService.validateUser(request.getUsername(), request.getPassword());

        if (isValid) {
            return Response.ok("{\"message\":\"Login Successful\"}").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("{\"message\":\"Invalid Credentials\"}").build();
        }
    }
}
