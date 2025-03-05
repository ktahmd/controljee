package com.transferconnect.controller;

import com.transferconnect.dao.ServiceDAO;
import com.transferconnect.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;

@Path("/ShowService")
public class ServiceTransServlet {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServicesName(@Context HttpServletRequest request) {


    	List<Service> Services =  ServiceDAO.getAllServices();
        if (Services == null || Services.isEmpty()) {
            return Response.ok().entity("{}" ).build();
        }

        return Response.ok(Services).build();
    }
}
