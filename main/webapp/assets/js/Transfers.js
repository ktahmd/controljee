document.addEventListener("DOMContentLoaded", function() {
    fetch(`/Controljee/api/transfers/session`)
        .then(response => response.json())
        .then(transfers => {
            const transfersTableBody = document.querySelector(".recent-transfers tbody");
            transfersTableBody.innerHTML = ""; // مسح المحتوى السابق

            if (!transfers || transfers.length === 0) {
                transfersTableBody.innerHTML = `<tr><td colspan="7">Aucun transfert trouvé</td></tr>`;
            } else {
                transfers.forEach(transfer => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>#${transfer.transferId}</td>
                        <td>${new Date(transfer.transferDate).toLocaleDateString()}</td>
                        <td>${transfer.receiverAccountId}</td>
                        <td>${transfer.bankservice}</td>
                        <td>${transfer.versbankservice}</td>
                        <td>${transfer.amount} MRU</td>
                        <td><span class="status ${transfer.status.toLowerCase()}">${transfer.status.toLowerCase()}</span></td>
                    `;
					
                    transfersTableBody.appendChild(row);
                });
            }
        })
        .catch(error => console.error("Erreur lors du chargement des transferts:", error));
});
