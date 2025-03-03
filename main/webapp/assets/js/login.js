
    document.getElementById("loginForm").addEventListener("submit", function(event) {
        event.preventDefault();
        
        let username = document.getElementById("username").value;
        let password = document.getElementById("password").value;

        fetch("/Controljee/api/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        })
        .then(response => response.json())
        .then(data => alert(data.message))
        .catch(error => console.error("Error:", error));
    });
