package com.transferconnect.dao;

import com.transferconnect.model.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceDAO {
    
    // Utilisation d'une Map pour stocker les services en mémoire
    private static Map<String, Service> services = new HashMap<>();
    
    static {
        // Initialisation avec des services par défaut
        services.put("BNK", new Service("BNK", "Bankily", true));
        services.put("MSV", new Service("MSV", "Masrivi", true));
        services.put("GHZ", new Service("GHZ", "Ghaza", true));
        services.put("MRS", new Service("MRS", "Marsoul", true));
    }
    
    // Récupérer un service par son ID
    public Service getServiceById(String serviceId) {
        if (serviceId == null) {
            throw new IllegalArgumentException("Service ID cannot be null");
        }
        return services.get(serviceId);
    }
    
    // Récupérer tous les services
    public List<Service> getAllServices() {
        return new ArrayList<>(services.values());
    }
    
    // Récupérer les services actifs
    public List<Service> getActiveServices() {
        return services.values().stream()
                .filter(Service::isActive)
                .collect(Collectors.toList());
    }
    
    // Créer un nouveau service
    public void createService(Service service) {
        if (service == null || service.getId() == null) {
            throw new IllegalArgumentException("Service or service ID cannot be null");
        }
        services.put(service.getId(), service);
    }
    
    // Mettre à jour un service existant
    public void updateService(Service service) {
        if (service == null || service.getId() == null) {
            throw new IllegalArgumentException("Service or service ID cannot be null");
        }
        if (services.containsKey(service.getId())) {
            services.put(service.getId(), service);
        }
    }
    
    // Affecter un responsable à un service
    public void assignManagerToService(String managerNni, String serviceId) {
        Service service = services.get(serviceId);
        if (service != null) {
            service.assignManager(managerNni);
        }
    }
    
    // Retirer un responsable d'un service
    public void removeManagerFromService(String managerNni, String serviceId) {
        Service service = services.get(serviceId);
        if (service != null) {
            service.removeManager(managerNni);
        }
    }
    
    // Récupérer tous les services assignés à un responsable spécifique
    public List<Service> getServicesByManager(String managerNni) {
        return services.values().stream()
                .filter(service -> service.hasManager(managerNni))
                .collect(Collectors.toList());
    }
    
    // Compter le nombre total de services
    public int countServices() {
        return services.size();
    }
}