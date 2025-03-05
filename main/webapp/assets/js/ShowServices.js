document.addEventListener("DOMContentLoaded", function() {
    fetch(`/Controljee/api/ShowService`)
        .then(response => response.json())
        .then(services => {
            const senderServiceSelect = document.getElementById("senderService");
            const receiverServiceSelect = document.getElementById("receiverService");
            
            senderServiceSelect.innerHTML = "";
            receiverServiceSelect.innerHTML = "";
            
            if (!services || services.length === 0) {
                senderServiceSelect.innerHTML = "<option>Aucun service disponible</option>";
                receiverServiceSelect.innerHTML = "<option>Aucun service disponible</option>";
            } else {
                services.forEach(service => {
                    if (service.active) {
                        const option = document.createElement("option");
                        option.value = service.id;
                        option.textContent = service.name;
                        
                        senderServiceSelect.appendChild(option.cloneNode(true));
                        receiverServiceSelect.appendChild(option);
                    }
                });
            }
        })
        .catch(error => console.error("Erreur lors du chargement des services:", error));
});