package com.transferconnect.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

import org.json.JSONObject;

@Path("/logout")
public class LogoutServlet {
    @POST
    public Response logout(@Context HttpServletRequest httpRequest, 
                       @Context HttpServletResponse httpResponse) throws IOException {
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate(); 
        }
        JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);
		jsonResponse.put("redirect", "/Controljee/login.html");
		System.out.println("Response Sent: " + jsonResponse);
		return Response.ok(jsonResponse.toString(), MediaType.APPLICATION_JSON).build();
        
		
    }
}