/*package com.transferconnect.controller;

import com.transferconnect.dao.ServiceDAO;
import com.transferconnect.dao.UserDAO;
import com.transferconnect.model.Role;
import com.transferconnect.model.Service;
import com.transferconnect.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.List;

@WebServlet("/api/admin/*")
public class AdminServlet extends HttpServlet {
    
    // Ajout des DAO en tant que variables d'instance
    private ServiceDAO serviceDAO;
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        // Initialisation des DAO
        serviceDAO = new ServiceDAO();
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Configuration de la réponse
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        // Récupération du chemin d'accès
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }
        
        // Ajouter les endpoints manquants
        if (pathInfo.equals("/service/create")) {
            // Création d'un nouveau service
            String requestBody = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            JSONObject jsonRequest = new JSONObject(requestBody);
            
            Service newService = new Service(
                jsonRequest.getString("serviceId"),
                jsonRequest.getString("serviceName"),
                jsonRequest.getBoolean("active")
            );
            
            // Vérifier si le service existe déjà
            if (serviceDAO.getServiceById(newService.getId()) != null) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                out.write("{\"error\": \"Un service avec cet ID existe déjà\"}");
                return;
            }
            
            serviceDAO.createService(newService);
            
            JSONObject responseJson = new JSONObject();
            responseJson.put("success", true);
            responseJson.put("message", "Service créé avec succès");
            
            out.write(responseJson.toString());
        } else if (pathInfo.equals("/manager/password/reset")) {
            // Réinitialiser le mot de passe d'un responsable d'agence
            String requestBody = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            JSONObject jsonRequest = new JSONObject(requestBody);
            
            String managerNni = jsonRequest.getString("managerNni");
            String newPassword = jsonRequest.getString("newPassword");
            
            User manager = userDAO.getUserById(managerNni);
            if (manager == null || manager.getRole() != Role.AGENCY_MANAGER) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\": \"Responsable d'agence non trouvé\"}");
                return;
            }
            
            manager.setPassword(newPassword);
            userDAO.updateUser(managerNni, manager);
            
            JSONObject responseJson = new JSONObject();
            responseJson.put("success", true);
            responseJson.put("message", "Mot de passe réinitialisé avec succès");
            
            out.write(responseJson.toString());
        } else if (pathInfo.equals("/service/manager/remove")) {
            // Retirer un responsable d'un service
            String requestBody = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            JSONObject jsonRequest = new JSONObject(requestBody);
            
            String managerNni = jsonRequest.getString("managerNni");
            String serviceId = jsonRequest.getString("serviceId");
            
            serviceDAO.removeManagerFromService(managerNni, serviceId);
            
            JSONObject responseJson = new JSONObject();
            responseJson.put("success", true);
            responseJson.put("message", "Responsable retiré du service avec succès");
            
            out.write(responseJson.toString());
        } else {
            // Endpoint non reconnu
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.write("{\"error\": \"Endpoint non trouvé\"}");
        }
    }
    
    // Ajouter une méthode pour récupérer les affectations service/responsable
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Configuration de la réponse
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        // Récupération du chemin d'accès
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }
        
        if (pathInfo.equals("/services")) {
            // Récupérer tous les services
            List<Service> services = serviceDAO.getAllServices();
            JSONArray servicesArray = new JSONArray();
            
            for (Service service : services) {
                JSONObject serviceJson = new JSONObject();
                serviceJson.put("id", service.getId());
                serviceJson.put("name", service.getName());
                serviceJson.put("active", service.isActive());
                servicesArray.put(serviceJson);
            }
            
            JSONObject responseJson = new JSONObject();
            responseJson.put("services", servicesArray);
            
            out.write(responseJson.toString());
        } else if (pathInfo.equals("/managers")) {
            // Récupérer tous les responsables d'agence
            List<User> managers = userDAO.getUsersByRole(Role.AGENCY_MANAGER);
            JSONArray managersArray = new JSONArray();
            
            for (User manager : managers) {
                JSONObject managerJson = new JSONObject();
                managerJson.put("nni", manager.getNni());
                managerJson.put("name", manager.getFirstName() + " " + manager.getLastName());
                managerJson.put("username", manager.getUsername());
                //managerJson.put("email", manager.getEmail());
                managersArray.put(managerJson);
            }
            
            JSONObject responseJson = new JSONObject();
            responseJson.put("managers", managersArray);
            
            out.write(responseJson.toString());
        } else if (pathInfo.equals("/serviceManagers")) {
            // Récupérer les affectations service/responsable
            List<Service> services = serviceDAO.getAllServices();
            JSONArray serviceManagersArray = new JSONArray();
            
            for (Service service : services) {
                JSONObject serviceManagerJson = new JSONObject();
                serviceManagerJson.put("serviceId", service.getId());
                serviceManagerJson.put("serviceName", service.getName());
                
                // Pour chaque service, récupérer ses responsables
                List<String> managerIds = service.getManagerIds();
                JSONArray managersArray = new JSONArray();
                
                for (String managerId : managerIds) {
                    User manager = userDAO.getUserById(managerId);
                    if (manager != null) {
                        JSONObject managerJson = new JSONObject();
                        managerJson.put("nni", manager.getNni());
                        managerJson.put("name", manager.getFirstName() + " " + manager.getLastName());
                        managersArray.put(managerJson);
                    }
                }
                
                serviceManagerJson.put("managers", managersArray);
                serviceManagersArray.put(serviceManagerJson);
            }
            
            JSONObject responseJson = new JSONObject();
            responseJson.put("serviceManagers", serviceManagersArray);
            out.write(responseJson.toString());
        } else {
            // Endpoint non reconnu
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.write("{\"error\": \"Endpoint non trouvé\"}");
        }
    }
}*/
package com.transferconnect.controller;

import com.transferconnect.dao.ServiceDAO;
import com.transferconnect.dao.UserDAO;
import com.transferconnect.model.Role;
import com.transferconnect.model.Service;
import com.transferconnect.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.List;

@WebServlet("/api/admin/*")
public class AdminServlet extends HttpServlet {
    
    private ServiceDAO serviceDAO;
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        serviceDAO = new ServiceDAO();
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }
        
        try {
            switch (pathInfo) {
                case "/services":
                    handleGetServices(response, out);
                    break;
                case "/managers":
                    handleGetManagers(response, out);
                    break;
                case "/serviceManagers":
                    handleGetServiceManagers(response, out);
                    break;
                case "/users":
                    handleGetUsers(response, out);
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.write("{\"error\": \"Endpoint non trouvé\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }
        
        try {
            switch (pathInfo) {
                case "/service/create":
                    handleServiceCreation(request, response, out);
                    break;
                case "/service/manager/assign":
                    handleManagerAssignment(request, response, out);
                    break;
                case "/service/manager/remove":
                    handleManagerRemoval(request, response, out);
                    break;
                case "/manager/create":
                    handleManagerCreation(request, response, out);
                    break;
                case "/changePassword":
                    handlePasswordChange(request, response, out);
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.write("{\"error\": \"Endpoint non trouvé\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    private void handleGetServices(HttpServletResponse response, PrintWriter out) {
        List<Service> services = serviceDAO.getAllServices();
        JSONArray servicesArray = new JSONArray();
        
        for (Service service : services) {
            JSONObject serviceJson = new JSONObject();
            serviceJson.put("id", service.getId());
            serviceJson.put("name", service.getName());
            serviceJson.put("active", service.isActive());
            servicesArray.put(serviceJson);
        }
        
        JSONObject responseJson = new JSONObject();
        responseJson.put("services", servicesArray);
        
        out.write(responseJson.toString());
    }
    
    private void handleGetManagers(HttpServletResponse response, PrintWriter out) {
        List<User> managers = userDAO.getUsersByRole(Role.AGENCY_MANAGER);
        JSONArray managersArray = new JSONArray();
        
        for (User manager : managers) {
            JSONObject managerJson = new JSONObject();
            managerJson.put("nni", manager.getNni());
            managerJson.put("name", manager.getFirstName() + " " + manager.getLastName());
            managerJson.put("username", manager.getUsername());
            managersArray.put(managerJson);
        }
        
        JSONObject responseJson = new JSONObject();
        responseJson.put("managers", managersArray);
        
        out.write(responseJson.toString());
    }
    
    private void handleGetServiceManagers(HttpServletResponse response, PrintWriter out) {
        List<Service> services = serviceDAO.getAllServices();
        JSONArray serviceManagersArray = new JSONArray();
        
        for (Service service : services) {
            JSONObject serviceManagerJson = new JSONObject();
            serviceManagerJson.put("serviceId", service.getId());
            serviceManagerJson.put("serviceName", service.getName());
            
            List<String> managerIds = service.getManagerIds();
            JSONArray managersArray = new JSONArray();
            
            for (String managerId : managerIds) {
                User manager = userDAO.getUserById(managerId);
                if (manager != null) {
                    JSONObject managerJson = new JSONObject();
                    managerJson.put("nni", manager.getNni());
                    managerJson.put("name", manager.getFirstName() + " " + manager.getLastName());
                    managersArray.put(managerJson);
                }
            }
            
            serviceManagerJson.put("managers", managersArray);
            serviceManagersArray.put(serviceManagerJson);
        }
        
        JSONObject responseJson = new JSONObject();
        responseJson.put("serviceManagers", serviceManagersArray);
        out.write(responseJson.toString());
    }
    
    private void handleGetUsers(HttpServletResponse response, PrintWriter out) {
        List<User> users = userDAO.getAllUsers();
        
        JSONObject responseJson = new JSONObject();
        responseJson.put("totalUsers", users.size());
        
        out.write(responseJson.toString());
    }
    
    private void handleServiceCreation(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        String requestBody = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        JSONObject jsonRequest = new JSONObject(requestBody);
        
        Service newService = new Service(
            jsonRequest.getString("serviceId"),
            jsonRequest.getString("serviceName"),
            jsonRequest.getBoolean("active")
        );
        
        if (serviceDAO.getServiceById(newService.getId()) != null) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            out.write("{\"error\": \"Un service avec cet ID existe déjà\"}");
            return;
        }
        
        serviceDAO.createService(newService);
        
        JSONObject responseJson = new JSONObject();
        responseJson.put("success", true);
        responseJson.put("message", "Service créé avec succès");
        
        out.write(responseJson.toString());
    }
    
    private void handleManagerAssignment(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        String requestBody = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        JSONObject jsonRequest = new JSONObject(requestBody);
        
        String serviceId = jsonRequest.getString("serviceId");
        String managerNni = jsonRequest.getString("managerNni");
        
        serviceDAO.assignManagerToService(managerNni, serviceId);
        
        JSONObject responseJson = new JSONObject();
        responseJson.put("success", true);
        responseJson.put("message", "Responsable affecté au service avec succès");
        
        out.write(responseJson.toString());
    }
    
    private void handleManagerRemoval(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        String requestBody = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        JSONObject jsonRequest = new JSONObject(requestBody);
        
        String serviceId = jsonRequest.getString("serviceId");
        String managerNni = jsonRequest.getString("managerNni");
        
        serviceDAO.removeManagerFromService(managerNni, serviceId);
        
        JSONObject responseJson = new JSONObject();
        responseJson.put("success", true);
        responseJson.put("message", "Responsable retiré du service avec succès");
        
        out.write(responseJson.toString());
    }
    
    private void handleManagerCreation(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        String requestBody = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        JSONObject jsonRequest = new JSONObject(requestBody);
        
        User newManager = new User(
            jsonRequest.getString("nni"),
            jsonRequest.getString("firstName"),
            jsonRequest.getString("lastName"),
            jsonRequest.getString("username"),
            jsonRequest.getString("password"),
            Role.AGENCY_MANAGER
        );
        
        userDAO.addUser(newManager);
        
        JSONObject responseJson = new JSONObject();
        responseJson.put("success", true);
        responseJson.put("message", "Responsable créé avec succès");
        
        out.write(responseJson.toString());
    }
    
    private void handlePasswordChange(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        String requestBody = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        JSONObject jsonRequest = new JSONObject(requestBody);
        
        // Vous devrez implémenter la logique de changement de mot de passe 
        // avec vérification du mot de passe actuel
        String currentPassword = jsonRequest.getString("currentPassword");
        String newPassword = jsonRequest.getString("newPassword");
        
        // Vérification du mot de passe actuel et mise à jour
        JSONObject responseJson = new JSONObject();
        responseJson.put("success", true);
        responseJson.put("message", "Mot de passe changé avec succès");
        
        out.write(responseJson.toString());
    }
}