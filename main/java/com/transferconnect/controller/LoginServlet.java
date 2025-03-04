package com.transferconnect.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import com.transferconnect.model.LoginRequest;
import com.transferconnect.model.User;
import com.transferconnect.service.UserService;

import java.io.IOException;

@Path("/login")
public class LoginServlet {
	private final UserService userService = new UserService();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(LoginRequest request, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse) {

		if (request == null || request.getUsername() == null || request.getPassword() == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"success\": false, \"message\": \"Invalid request format\"}")
					.type(MediaType.APPLICATION_JSON).build();
		}

		boolean isValid = userService.validateUser(request.getUsername(), request.getPassword());


        if (isValid) {
            // Correction de l'appel pour récupérer l'utilisateur
            User user = userService.getUserByUsername(request.getUsername());

            // Vérifier si l'utilisateur existe avant d'ajouter ses infos à la session
            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"success\": false, \"message\": \"Invalid username or password\"}")
                        .type(MediaType.APPLICATION_JSON).build();
            }
			HttpSession session = httpRequest.getSession(true);
			session.setAttribute("username", request.getUsername());
			session.setAttribute("firstName", user.getFirstName());
			session.setAttribute("lastName", user.getLastName());
			System.out.println("Session Username: " + session.getAttribute("username"));
			

			JSONObject jsonResponse = new JSONObject();
			jsonResponse.put("success", true);
			jsonResponse.put("redirect", "/Controljee/dashboard.html");
			System.out.println("Response Sent: " + jsonResponse);
			return Response.ok(jsonResponse.toString(), MediaType.APPLICATION_JSON).build();

		} else {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity("{\"success\": false, \"message\": \"Invalid username or password\"}")
					.type(MediaType.APPLICATION_JSON).build();
		}
	}

}