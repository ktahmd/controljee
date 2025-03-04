package com.transferconnect.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

@Path("/session")
public class UserSessionServlet {

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserSessionData(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("username") == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"success\": false, \"message\": \"User not logged in\"}")
                    .type(MediaType.APPLICATION_JSON).build();
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", true);
        jsonResponse.put("username", session.getAttribute("username"));
        jsonResponse.put("firstName", session.getAttribute("firstName"));
        jsonResponse.put("lastName", session.getAttribute("lastName"));

        return Response.ok(jsonResponse.toString(), MediaType.APPLICATION_JSON).build();
    }
}

