document.getElementById("logout-button").addEventListener("click", function() {

    fetch("/Controljee/api/logout", { method: "POST" })
    .then(response => response.json())  // Convertir la réponse en JSON
    .then(data => {
        if (data.success) {
            window.location.href = data.redirect;  // Redirection vers la page de connexion

        } else {
            console.error("Échec de la déconnexion :", data.message);
            alert("Une erreur s'est produite lors de la déconnexion !");  // Alerte pour l'utilisateur
        }
    })
    .catch(error => console.error("Erreur :", error));
});
