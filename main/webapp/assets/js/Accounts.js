document.addEventListener("DOMContentLoaded", function() {
    fetch(`/Controljee/api/accounts/session`)
        .then(response => response.json())
        .then(accounts => {
            const statsContainer = document.querySelector(".stats-container");
            statsContainer.innerHTML = ""; // مسح المحتوى السابق

            if (!accounts || Object.keys(accounts).length === 0) {
                statsContainer.innerHTML = `<div class="stat-card">
                    <div class="stat-title">Banque</div>
                    <div class="stat-value">Aucun compte</div>
                </div>`;
            } else {
                Object.values(accounts).forEach(account => {
                    const card = document.createElement("div");
                    card.classList.add("stat-card");
                    card.innerHTML = `
                        <div class="stat-title">${account.bank}</div>
                        <div class="stat-value">${account.balance} MRU</div>
                    `;
                    statsContainer.appendChild(card);
                });
            }
        })
        .catch(error => console.error("Erreur lors du chargement des comptes:", error));
});