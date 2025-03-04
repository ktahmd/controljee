document.getElementById("registerForm").addEventListener("submit", function(event) {
    event.preventDefault();
    
    let nni = document.getElementById("nni").value;
    let firstName = document.getElementById("firstName").value;
    let lastName = document.getElementById("lastName").value;
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    fetch("/Controljee/api/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ 
            nni: nni,
            firstName: firstName,
            lastName: lastName,
            username: username, 
            password: password,
            role: "USER" // Default role for new registrations
        })
    })
    .then(response => response.json())
    .then(data => {
        alert(data.message);
        if (data.success) {
            window.location.href = "login.html"; // Redirect to login page on success
        }
    })
    .catch(error => console.error("Error:", error));
});