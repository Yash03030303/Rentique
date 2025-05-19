document.getElementById("loginForm").addEventListener("submit", function (e) {
	e.preventDefault();

	const loginData = {
		email: document.getElementById("email").value,
		password: document.getElementById("password").value,
	};

	fetch("http://localhost:8080/auth/signin", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(loginData),
	})
		.then((response) => {
			if (!response.ok) {
				throw new Error("Login failed");
			}
			return response.json();
		})
		.then((data) => {
			// Store the JWT token
			localStorage.setItem("token", data.token);

			// Decode the JWT token to get user type
			const tokenPayload = decodeJWT(data.token);

			if (!tokenPayload) {
				throw new Error("Invalid token");
			}

			// Redirect based on user type
			switch (tokenPayload.usertype.toLowerCase()) {
				case "supplier":
					window.location.href = "supplier.html";
					break;
				case "user":
					window.location.href = "customer.html";
					break;
				default:
					throw new Error("Invalid user type");
			}
		})
		.catch((error) => {
			document.getElementById("error").innerHTML =
				'<div class="alert alert-danger">Invalid email or password</div>';
			console.error("Login error:", error);
		});
});
