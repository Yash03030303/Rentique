fetchRentals();

function fetchRentals() {
	const token = localStorage.getItem("token");
	console.log("Token present:", !!token);

	if (!token) {
		window.location.href = "/login.html";
		return;
	}

	fetch("http://localhost:8080/api/rentals", {
		method: "GET",
		headers: {
			Authorization: `Bearer ${token}`,
			"Content-Type": "application/json",
		},
	})
		.then(async (response) => {
			if (!response.ok) {
				const errorText = await response.text();
				console.error("Response error:", errorText);
				throw new Error("Network error");
			}
			return response.json();
		})
		.then((data) => {
			if (!Array.isArray(data)) {
				console.error("Expected array but got:", data);
				return;
			}
			displayRentals(data);
		})
		.catch((error) => {
			console.error("Error fetching rentals:", error);
			alert("Failed to load rentals. Please try again later.");
		});
}

function displayRentals(rentals) {
	const tableBody = document.getElementById("tbody");
	if (!tableBody) return;

	tableBody.innerHTML = "";

	rentals.forEach((rental) => {
		const rentalItems = rental.rentalItems?.map(item =>
			`Item#${item.rentalItemId} (Qty: ${item.quantity}, Days: ${item.daysRented})`
		).join("<br>") || "No Items";

		let returnStatus;
		if (rental.returns && rental.returns.length > 0) {
			const ret = rental.returns[0];
			returnStatus = `Returned on ${new Date(ret.returnDate).toLocaleDateString("en-IN")}<br>
                      Condition: ${ret.itemCondition}<br>
                      Late Fee: ₹${ret.lateFee.toFixed(2)}`;
		} else {
			const encodedRental = encodeURIComponent(JSON.stringify(rental));
			returnStatus = `<span class="text-danger font-weight-bold">Not Returned</span><br>
        <button class="btn btn-sm btn-success mt-1" onclick="returnRental('${encodedRental}')">Return</button>`;
		}

		const row = document.createElement("tr");
		row.innerHTML = `
        <td>${rental.rentalId ?? "N/A"}</td>
        <td>${rentalItems}</td>
        <td>${rental.rentalDate ? new Date(rental.rentalDate).toLocaleDateString("en-IN") : "N/A"}</td>
        <td>${rental.dueDate ? new Date(rental.dueDate).toLocaleDateString("en-IN") : "N/A"}</td>
        <td>₹${rental.totalAmount ? rental.totalAmount.toFixed(2) : "0.00"}</td>
        <td>${returnStatus}</td>
    `;
		tableBody.appendChild(row);
	});
}

function returnRental(encodedRental) {
	const rental = JSON.parse(decodeURIComponent(encodedRental));

	const itemCondition = prompt("Enter item condition (e.g., Good, Damaged):");
	if (!itemCondition) return;

	const token = localStorage.getItem("token");
	const returnDate = new Date();
	const dueDate = new Date(rental.dueDate);

	

	fetch("http://localhost:8080/api/returns", {
		method: "POST",
		headers: {
			Authorization: `Bearer ${token}`,
			"Content-Type": "application/json",
		},
		body: JSON.stringify({
			rental: rental,
			returnDate: returnDate.toISOString().split("T")[0],
			itemCondition: itemCondition,
		}),
	})
		.then((response) => {
			if (!response.ok) {
				return response.text().then((text) => {
					throw new Error(text);
				});
			}
			return response.json();
		})
		.then((data) => {
			alert("Return processed successfully!");
			fetchRentals(); // reload updated list
		})
		.catch((err) => {
			console.error("Error processing return:", err);
			alert("Failed to process return.");
		});
}