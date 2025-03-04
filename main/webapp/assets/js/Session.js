document.addEventListener("DOMContentLoaded", function () {
    fetch("/Controljee/api/session/user")
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                document.getElementById("user-fullname").textContent = data.firstName + " " + data.lastName;
            } else {
                console.log("User not logged in");
                window.location.href = "login.html"; // Rediriger vers la page de connexion si non connecté
            }
        })
        .catch(error => console.error("Error fetching user session data:", error));
});
