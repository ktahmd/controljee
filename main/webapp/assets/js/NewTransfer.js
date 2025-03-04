document.getElementById("transferForm").addEventListener("submit", function(event) {
    event.preventDefault();

    let senderService = document.getElementById("senderService").value;
    let receiverService = document.getElementById("receiverService").value;
    let receiverNumber = document.getElementById("receiverNumber").value;
    let amount = parseFloat(document.getElementById("amount").value);
    let reason = document.getElementById("reason").value;

    if (!receiverNumber || isNaN(amount) || amount <= 0) {
        alert("Veuillez entrer des informations valides.");
        return;
    }

    fetch("/Controljee/api/transfers/new", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            senderService: senderService,
            receiverService: receiverService,
            receiverNumber: receiverNumber,
            amount: amount,
            reason: reason
        })
    })
    .then(response => response.json())
    .then(data => {
        alert(data.message);
        if (data.success) {
            window.location.reload();
        }
		window.location.reload();
    })
    .catch(error => console.error("Erreur :", error));

});
