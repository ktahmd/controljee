
// Ajouter une fonction pour charger les statistiques
/*function loadDashboard() {
    // Charger les statistiques des services
    fetchServiceCount();
    
    // Charger les statistiques des responsables
    fetchManagerCount();
    
    // Charger les statistiques des utilisateurs
    fetchUserCount();
    
    // Charger les données des services
    loadServices();
    
    // Charger les données des responsables
    loadManagers();
    
    // Charger les affectations service/responsable
    loadServiceManagers();
}

// Fonction pour récupérer le nombre total d'utilisateurs
function fetchUserCount() {
    fetch('/Controljee/api/admin/users')
        .then(response => response.json())
        .then(data => {
            document.getElementById('users-count').textContent = data.totalUsers || '0';
        })
        .catch(error => console.error('Erreur de chargement des utilisateurs:', error));
}*/
document.addEventListener('DOMContentLoaded', () => {
    // Navigation entre les sections
    const navLinks = document.querySelectorAll('nav a');
    const sections = document.querySelectorAll('main section');

    navLinks.forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            
            // Retirer la classe active de tous les liens et sections
            navLinks.forEach(l => l.classList.remove('active'));
            sections.forEach(s => s.classList.remove('active'));
            
            // Ajouter la classe active au lien et à la section correspondante
            const sectionId = link.getAttribute('data-section');
            link.classList.add('active');
            document.getElementById(sectionId).classList.add('active');

            // Charger les données spécifiques à la section
            switch(sectionId) {
                case 'dashboard':
                    loadDashboard();
                    break;
                case 'services':
                    loadServices();
                    break;
                case 'managers':
                    loadManagers();
                    break;
                case 'assignments':
                    loadServiceManagers();
                    break;
            }
        });
    });

    // Charger le tableau de bord par défaut
    loadDashboard();
    setupEventListeners();
});

function loadDashboard() {
    fetchServiceCount();
    fetchManagerCount();
    fetchUserCount();
}

function fetchServiceCount() {
    fetch('/Controljee/api/admin/services')
        .then(response => response.json())
        .then(data => {
            document.getElementById('services-count').textContent = data.services.length || '0';
        })
        .catch(error => console.error('Erreur de chargement des services:', error));
}

function fetchManagerCount() {
    fetch('/Controljee/api/admin/managers')
        .then(response => response.json())
        .then(data => {
            document.getElementById('managers-count').textContent = data.managers.length || '0';
        })
        .catch(error => console.error('Erreur de chargement des responsables:', error));
}

function fetchUserCount() {
    fetch('/Controljee/api/admin/users')
        .then(response => response.json())
        .then(data => {
            document.getElementById('users-count').textContent = data.totalUsers || '0';
        })
        .catch(error => console.error('Erreur de chargement des utilisateurs:', error));
}

function loadServices() {
    fetch('/Controljee/api/admin/services')
        .then(response => response.json())
        .then(data => {
            const serviceTable = document.querySelector('#services-table tbody');
            serviceTable.innerHTML = '';
            
            data.services.forEach(service => {
                const row = `
                    <tr>
                        <td>${service.id}</td>
                        <td>${service.name}</td>
                        <td>${service.active ? 'Actif' : 'Inactif'}</td>
                        <td>
                            <button onclick="editService('${service.id}')">Modifier</button>
                        </td>
                    </tr>
                `;
                serviceTable.innerHTML += row;
            });
        })
        .catch(error => console.error('Erreur de chargement des services:', error));
}

function loadManagers() {
    fetch('/Controljee/api/admin/managers')
        .then(response => response.json())
        .then(data => {
            const managersTable = document.querySelector('#managers-table tbody');
            managersTable.innerHTML = '';
            
            data.managers.forEach(manager => {
                const row = `
                    <tr>
                        <td>${manager.nni}</td>
                        <td>${manager.name}</td>
                        <td>${manager.username}</td>
                        <td>
                            <button onclick="resetManagerPassword('${manager.nni}')">Réinitialiser MDP</button>
                        </td>
                    </tr>
                `;
                managersTable.innerHTML += row;
            });
        })
        .catch(error => console.error('Erreur de chargement des responsables:', error));
}

function loadServiceManagers() {
    fetch('/Controljee/api/admin/serviceManagers')
        .then(response => response.json())
        .then(data => {
            const assignmentsTable = document.querySelector('#assignments-table tbody');
            assignmentsTable.innerHTML = '';
            
            data.serviceManagers.forEach(serviceManager => {
                const managersNames = serviceManager.managers.map(m => m.name).join(', ');
                const row = `
                    <tr>
                        <td>${serviceManager.serviceName}</td>
                        <td>${managersNames}</td>
                        <td>
                            <button onclick="removeManagerFromService('${serviceManager.serviceId}')">Retirer</button>
                        </td>
                    </tr>
                `;
                assignmentsTable.innerHTML += row;
            });
        })
        .catch(error => console.error('Erreur de chargement des affectations:', error));
}

function setupEventListeners() {
    // Placeholder for future event listeners
    // Par exemple : gestion des boutons d'ajout, de modification, etc.
}

// Fonctions de gestion à implémenter
function editService(serviceId) {
    console.log('Éditer le service:', serviceId);
    // Logique pour ouvrir le modal d'édition de service
}

function resetManagerPassword(managerNni) {
    console.log('Réinitialiser le mot de passe pour:', managerNni);
    // Logique pour réinitialiser le mot de passe
}

function removeManagerFromService(serviceId) {
    console.log('Retirer le responsable du service:', serviceId);
    // Logique pour retirer un responsable d'un service
}
// Ajouter ces fonctions à votre admin.js existant

function openServiceModal() {
    const modal = document.getElementById('service-modal');
    modal.style.display = 'block';
}

function closeServiceModal() {
    const modal = document.getElementById('service-modal');
    modal.style.display = 'none';
}

function handleServiceSubmit(event) {
    event.preventDefault();
    
    const serviceCode = document.getElementById('service-code').value;
    const serviceName = document.getElementById('service-name').value;
    const serviceStatus = document.getElementById('service-status').value;
    
    fetch('/Controljee/api/admin/service/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            serviceId: serviceCode,
            serviceName: serviceName,
            active: serviceStatus === 'true'
        })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('Service ajouté avec succès');
            closeServiceModal();
            loadServices(); // Recharger la liste des services
        } else {
            alert('Erreur : ' + data.error);
        }
    })
    .catch(error => {
        console.error('Erreur:', error);
        alert('Une erreur est survenue lors de l\'ajout du service');
    });
}

function setupEventListeners() {
    // Ajouter ces écouteurs d'événements
    const addServiceBtn = document.getElementById('add-service-btn');
    if (addServiceBtn) {
        addServiceBtn.addEventListener('click', openServiceModal);
    }
    
    const serviceForm = document.getElementById('service-form');
    if (serviceForm) {
        serviceForm.addEventListener('submit', handleServiceSubmit);
    }
    
    const closeModalButtons = document.querySelectorAll('#service-modal .close');
    closeModalButtons.forEach(button => {
        button.addEventListener('click', closeServiceModal);
    });
}

// Ajouter cet appel à la fin de votre DOMContentLoaded event listener
setupEventListeners();
