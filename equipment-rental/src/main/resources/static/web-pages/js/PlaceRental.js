
	let equipmentList = [];
	let cart = [];
	const token = localStorage.getItem("token");
	fetchEquipments();

	function fetchEquipments() {
		fetch("http://localhost:8080/api/equipment", {
			method: "GET",
			headers: {
				Authorization: `Bearer ${token}`,
				"Content-Type": "application/json",
			},
		})
			.then((res) => res.json())
			.then((data) => {
				equipmentList = data;
				const select = document.getElementById("equipmentSelect");
				data.forEach((eq) => {
					const option = document.createElement("option");
					option.value = eq.equipmentId;
					option.textContent = `${eq.name} (₹${eq.rentalPricePerDay}/day)`;
					select.appendChild(option);
				});
			})
			.catch((err) => console.error("Error fetching equipments:", err));
	}

	function addToCart() {
		const eqId = document.getElementById("equipmentSelect").value;
		const duration = parseInt(document.getElementById("duration").value);
		if (!eqId || duration < 1) return alert("Invalid input");

		const eq = equipmentList.find((e) => e.equipmentId == eqId);
		if (!eq) return alert("Equipment not found");

		const existing = cart.find((item) => item.id == eqId);
		if (existing) {
			existing.duration += duration;
		} else {
			cart.push({ ...eq, duration });
		}
		renderCart();
	}

	function renderCart() {
		const tbody = document.getElementById("cartBody");
		tbody.innerHTML = "";

		if (cart.length === 0) {
			tbody.innerHTML =
				'<tr><td colspan="5" class="text-center">No items in cart</td></tr>';
			document.getElementById("totalAmount").textContent = "0";
			return;
		}

		let total = 0;
		cart.forEach((item, idx) => {
			const subtotal = item.rentalPricePerDay * item.duration;
			total += subtotal;

			const tr = document.createElement("tr");
			tr.innerHTML = `
      <td>${item.name}</td>
      <td>₹${item.rentalPricePerDay}</td>
      <td>${item.duration}</td>
      <td>₹${subtotal}</td>
      <td><button class="btn btn-sm btn-danger" onclick="removeFromCart(${idx})">
        <i class="fas fa-trash"></i></button></td>
    `;
			tbody.appendChild(tr);
		});

		document.getElementById("totalAmount").textContent = total;
	}

	function removeFromCart(index) {
		cart.splice(index, 1);
		renderCart();
	}
	function placeOrder() {
		if (cart.length === 0) return alert("Cart is empty");

		const token = localStorage.getItem("token");
		const user = getUserID(token);

		if (!token) {
			alert("User not authenticated");
			return;
		}

		const rentalDate = new Date();
		const maxDuration = Math.max(...cart.map((item) => item.duration));
		const dueDate = new Date();
		dueDate.setDate(rentalDate.getDate() + maxDuration);

		const totalAmount = cart.reduce((sum, item) => {
			return sum + item.rentalPricePerDay * item.duration;
		}, 0);

		const payload = {
			rentalDate: rentalDate.toISOString().split("T")[0],
			dueDate: dueDate.toISOString().split("T")[0],
			totalAmount,
			user: {
				userId: user,
			},
			rentalItems: cart.map((item) => ({
				equipment: { equipmentId: item.equipmentId },
				quantity: 1,
				daysRented: item.duration,
			})),
		};

		fetch("http://localhost:8080/api/rentals", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
				Authorization: `Bearer ${token}`,
			},
			body: JSON.stringify(payload),
		})
			.then((res) => {
				if (!res.ok) throw new Error("Order failed");
				return res.json();
			})
			.then((data) => {
				showSuccessMessage("Order Placed Successfully!");
				cart = [];
				renderCart();
			})
			.catch((err) => {
				console.error(err);
				alert("Failed to place order.");
			});
	}
	function showSuccessMessage(message) {
		const existingDiv = document.getElementById("orderSuccessMessage");
		if (existingDiv) existingDiv.remove();

		const div = document.createElement("div");
		div.id = "orderSuccessMessage";
		div.innerHTML = `
		<div style="
			position: fixed;
			top: 15px;
			right: 15px;
			background-color: rgb(88, 172, 71);
			color: white;
			font-weight: bold;
			padding: 8px 12px;
			border-radius: 6px;
			box-shadow: 0 0 6px rgba(134, 175, 58, 0.3);
			z-index: 9999;
			display: flex;
			align-items: center;
			justify-content: space-between;
			min-width: 220px;
			max-width: 300px;
			font-size: 14px;
		">
			<span>${message}</span>
			<span style="margin-left: 10px; cursor: pointer; font-weight: bold;" onclick="document.getElementById('orderSuccessMessage').remove()">×</span>
		</div>
	`;
		document.body.appendChild(div);

		// Auto remove after 7 seconds
		setTimeout(() => {
			const elem = document.getElementById("orderSuccessMessage");
			if (elem) elem.remove();
		}, 5000);
	}
