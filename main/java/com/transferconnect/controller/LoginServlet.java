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
import com.transferconnect.model.Role;
import com.transferconnect.service.UserService;

@Path("/login")
public class LoginServlet {
    private final UserService userService = new UserService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request, @Context HttpServletRequest httpRequest,
            @Context HttpServletResponse httpResponse) {

        // Valider les paramètres de la requête
        if (request == null || request.getUsername() == null || request.getPassword() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"success\": false, \"message\": \"Format de requête invalide\"}")
                    .type(MediaType.APPLICATION_JSON).build();
        }

        // Valider les identifiants
        boolean isValid = userService.validateUser(request.getUsername(), request.getPassword());

        if (isValid) {
            // Récupérer l'utilisateur
            User user = userService.getUserByUsername(request.getUsername());

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"success\": false, \"message\": \"Utilisateur non trouvé\"}")
                        .type(MediaType.APPLICATION_JSON).build();
            }

            // Créer une session
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("firstName", user.getFirstName());
            session.setAttribute("lastName", user.getLastName());
            session.setAttribute("role", user.getRole().toString());
            session.setAttribute("nni", user.getNni());

            // Préparer la réponse JSON
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", true);
            
            // Redirection basée sur le rôle
            switch (user.getRole()) {
                case ADMIN:
                    jsonResponse.put("redirect", "/Controljee/admin-dashboard.html");
                    break;
                case AGENCY_MANAGER:
                    jsonResponse.put("redirect", "/Controljee/dashboard.html");
                    break;
                case USER:
                    jsonResponse.put("redirect", "/Controljee/dashboard.html");
                    break;
                default:
                    jsonResponse.put("redirect", "/Controljee/dashboard.html");
            }

            return Response.ok(jsonResponse.toString(), MediaType.APPLICATION_JSON).build();

        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"success\": false, \"message\": \"Nom d'utilisateur ou mot de passe incorrect\"}")
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }
}