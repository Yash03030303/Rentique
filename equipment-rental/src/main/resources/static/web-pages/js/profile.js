 function L(){
	const token = localStorage.getItem("token");
	const decoded = decodeJWT(token);
	const userId = decoded.userid;

	fetch(`http://localhost:8080/api/users/${userId}`, {
		headers: {
			Authorization: `Bearer ${token}`,
			"Content-Type": "application/json",
		},
	})
		.then((response) => {
			if (!response.ok) throw new Error("Failed to fetch user profile");
			return response.json();
		})
		.then((profile) => {
			const t = decoded.usertype.charAt(0).toUpperCase() + decoded.usertype.slice(1).toLowerCase();
			document.getElementById("profileName").textContent = decoded.name || "N/A";
			document.getElementById("profileEmail").textContent = decoded.email || "N/A";
			document.getElementById("profileType").textContent = t || "N/A";
			document.getElementById("profilePhone").textContent = profile.phone || "N/A";
		})
		.catch((error) => {
			console.error("Error loading profile:", error);
			document.getElementById("main-content").innerHTML =
				"<div class='alert alert-danger'>Failed to load admin profile.</div>";
		});
};
L();