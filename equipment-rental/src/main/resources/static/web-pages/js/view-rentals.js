function filterRentals(rentals) {
    const minPrice = parseFloat(document.getElementById('minPrice').value) || 0;
    const maxPrice = parseFloat(document.getElementById('maxPrice').value) || Infinity;
    
    return rentals.filter(rental => {
        const amount = parseFloat(rental.totalAmount);
        return amount >= minPrice && amount <= maxPrice;
    });
}

function loadRentals() {
    const token = localStorage.getItem("token");
    const URL = "http://localhost:8080";
    fetch(`${URL}/api/rentals`, {
	headers: {
		Authorization: `Bearer ${token}`,
		"Content-Type": "application/json",
	},
})
	.then((response) => {
		if (!response.ok) throw new Error("Failed to fetch rentals");
		return response.json();
	})
	.then((data) => {
		const tbody = document.getElementById("rentalTableBody");
		tbody.innerHTML = "";

		const activeRentals = filterRentals(data.filter(
			(rental) => !rental.returns || rental.returns.length === 0
		));
		if (activeRentals.length === 0) {
			tbody.innerHTML = `<tr><td colspan="5" class="text-center">No active rentals found.</td></tr>`;
			return;
		}
		activeRentals.forEach((rental) => {
			const row = document.createElement("tr");

			row.innerHTML = `
          <td>${rental.rentalId}</td>
          <td>${rental.user?.userId || "N/A"}</td>
          <td>${rental.rentalDate}</td>
          <td>${rental.dueDate}</td>
          <td>₹ ${rental.totalAmount}</td>
        `;
			tbody.appendChild(row);
		});
	})
	.catch((error) => {
		console.error("Error loading rentals:", error);
		document.getElementById(
			"rentalTableBody"
		).innerHTML = `<tr><td colspan="5" class="text-danger text-center">Failed to load rentals.</td></tr>`;
	});
}

// Add event listeners for filters
document.getElementById('minPrice').addEventListener('input', loadRentals);
document.getElementById('maxPrice').addEventListener('input', loadRentals);

// Initial load
loadRentals();