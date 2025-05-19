function P() {
	const token = localStorage.getItem("token");
	const url = "http://localhost:8080";
	const apiUrl = `${url}/api/equipment`;
	const tableBody = document.getElementById("equipmentTableBody");
	const searchInput = document.getElementById("equipmentSearchInput");
	let equipmentList = [];

	function fetchEquipment() {
		fetch(apiUrl, {
			method: "GET",
			headers: {
				Authorization: `Bearer ${token}`,
				"Content-Type": "application/json",
			},
		})
			.then((res) => {
				if (!res.ok) throw new Error("Failed to fetch equipment.");
				return res.json();
			})
			.then((data) => {
				equipmentList = data;
				renderEquipment(getFilteredData());
			})
			.catch((err) => {
				console.error("Error loading equipment:", err);
				tableBody.innerHTML = `<tr><td colspan="4" class="text-danger">Failed to load equipment data.</td></tr>`;
			});
	}

	// Filter equipment based on search input
	function getFilteredData() {
		const searchTerm = searchInput.value.toLowerCase();
		return equipmentList.filter((e) =>
			e.name.toLowerCase().includes(searchTerm)
		);
	}

	// Render equipment table
	function renderEquipment(data) {
		tableBody.innerHTML = "";

		if (data.length === 0) {
			tableBody.innerHTML = `<tr><td colspan="4" class="text-warning">No matching equipment found.</td></tr>`;
			return;
		}

		data.forEach((eq) => {
			const row = document.createElement("tr");
			row.innerHTML = `
        <td>${eq.equipmentId}</td>
        <td>${eq.name}</td>
        <td>₹${eq.rentalPricePerDay}</td>
        <td>${eq.stock}</td>
      `;
			tableBody.appendChild(row);
		});
	}

	// Search event
	searchInput.addEventListener("input", () => {
		renderEquipment(getFilteredData());
	});

	// Initial fetch
	fetchEquipment();
}
P();