document.addEventListener("DOMContentLoaded", () => {
	const form = document.querySelector("form");
	const nameInput = form.querySelector('input[type="text"]');
	const emailInput = form.querySelector('input[type="email"]');
	const passwordInput = form.querySelector('input[type="password"]');
	const phoneInput = form.querySelector('input[type="tel"]');
	const userTypeInput = form.querySelector("select");

	form.addEventListener("submit", function (e) {
		e.preventDefault();

		const name = nameInput.value.trim();
		const email = emailInput.value.trim();
		const password = passwordInput.value.trim();
		const phone = phoneInput.value.trim();
		const userType = userTypeInput.value;

		// Client-side validation
		const errors = [];

		if (name.length < 2) {
			errors.push("Name must be at least 2 characters.");
		}

		const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		if (!emailRegex.test(email)) {
			errors.push("Invalid email format.");
		}

		const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\W).{8,}$/;
		if (!passwordRegex.test(password)) {
			errors.push(
				"Password must be at least 8 characters, including uppercase, lowercase, and a special character."
			);
		}

		const phoneRegex = /^[7-9]\d{9}$/;
		if (!phoneRegex.test(phone)) {
			errors.push("Phone number must be 10 digits and start with 7, 8, or 9.");
		}

		if (!userType) {
			errors.push("User type is required.");
		}

		if (errors.length > 0) {
			alert(errors.join("\n"));
			return;
		}

		// Prepare user object
		const user = {
			name,
			email,
			password,
			phone,
			userType,
		};

		// Send POST request
		fetch("http://localhost:8080/auth/register", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify(user),
		})
			.then((response) => {
				if (response.ok) {
					alert("Registration successful! Redirecting to login...");
					window.location.href = "login.html";
				} else if (response.status === 409) {
					throw new Error("Email already exists.");
				} else {
					return response.json().then((data) => {
						throw new Error(data.message || "Registration failed.");
					});
				}
			})
			.catch((error) => {
				alert(`Error: ${error.message}`);
			});
	});
});
