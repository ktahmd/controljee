document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault();
	let username = document.getElementById("username").value;
	let password = document.getElementById("password").value;
	    fetch("/Controljee/api/login", {
	        method: "POST",
	        headers: { "Content-Type": "application/json" },
	        body: JSON.stringify({ username, password })
	    })
	    .then(response => {
	        return response.json(); // Ensure response is parsed as JSON
	    })
	    .then(data => {
	        console.log("Response Data:", data);
	        if (data.success) {
	            window.location.href = data.redirect;
	        } else {
	            alert(data.message);
	        }
	    })
	    .catch(error => {
	        console.error("Login Error:", error);
	        alert("An error occurred. Check console for details.");
	    });
	});