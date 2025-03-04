package com.transferconnect.controller;

import com.transferconnect.model.*;
import com.transferconnect.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.Map;

@Path("/accounts")
public class AccountServlet {
    @GET
    @Path("/session")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSessionUserAccounts(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Utilisateur non connecté").build();
        }

        Map<String, Account> userAccounts = UserService.getInstance().getAllAccountsBySessionUser(session);
        if (userAccounts == null || userAccounts.isEmpty()) {
            return Response.ok().entity("{}" ).build();
        }

        return Response.ok(userAccounts).build();
    }
}