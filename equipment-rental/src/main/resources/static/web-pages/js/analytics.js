function initAnalytics() {
	token = localStorage.getItem("token");
	if (!token) {
		alert("You must be logged in.");
		window.location.href = "login.html";
		return;
	}
const userId = getUserID(token);
	

	fetch(`http://localhost:8080/api/analytics/user/${userId}`, {
		headers: {
			Authorization: `Bearer ${token}`
		}
	})
		.then((response) => {
			if (!response.ok) {
				throw new Error("Failed to fetch analytics data");
			}
			return response.json();
		})
		.then((data) => {
			document.getElementById("totalSpent").textContent = `₹${data.totalAmountSpent.toFixed(2)}`;
			document.getElementById("avgRental").textContent = `₹${data.averageRentalCost.toFixed(2)}`;

			 topRentedList = document.getElementById("topRented");
			topRentedList.innerHTML = "";
			data.topRentedEquipment.forEach(item => {
				 li = document.createElement("li");
				li.className = "list-group-item";
				li.textContent = `${item.equipmentName} (${item.rentalCount} times)`;
				topRentedList.appendChild(li);
			});

			document.getElementById("longestRentalEquipment").textContent = data.longestRental.equipmentName;
			document.getElementById("longestRentalDuration").textContent = `${data.longestRental.durationDays} days`;

			 months = data.monthlyBreakdown.map(item => item.month);
			 amounts = data.monthlyBreakdown.map(item => item.amount);

			 ctx = document.getElementById("monthlyBreakdownChart").getContext("2d");
			new Chart(ctx, {
				type: "bar",
				data: {
					labels: months,
					datasets: [{
						label: "Amount Spent (₹)",
						data: amounts,
						backgroundColor: "#0d6efd"
					}]
				},
				options: {
					responsive: true,
					scales: {
						y: {
							beginAtZero: true
						}
					}
				}
			});
		})
		.catch(err => {
			console.error("Analytics fetch error:", err);
			alert("Could not load analytics data.");
		});
}

initAnalytics();