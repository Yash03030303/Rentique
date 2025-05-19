(() => {
    const API_BASE_URL = 'http://localhost:8080';
    const token = localStorage.getItem("token");

    // Handle form submission
    document.getElementById('equipmentForm').addEventListener('submit', function (e) {
        e.preventDefault();

        const formData = {
            equipmentId: document.getElementById('equipmentId').value || null,
            name: document.getElementById('name').value,
            rentalPricePerDay: parseFloat(document.getElementById('rentalPricePerDay').value),
            stock: parseInt(document.getElementById('stock').value),
            categories: {
                categoryId: parseInt(document.getElementById('categoryId').value)
            }
        };

        if (formData.equipmentId) {
            updateEquipment(formData.equipmentId, formData)
                .then(() => {
                    resetForm();
                    loadEquipmentData();
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('An error occurred while saving the equipment.');
                });
        } else {
            createEquipment(formData)
                .then(() => {
                    resetForm();
                    loadEquipmentData();
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('An error occurred while saving the equipment.');
                });
        }
    });

    // Load categories
    function loadCategories() {
        fetch(`${API_BASE_URL}/api/categories`, {
            method: "GET",
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
            },
        })
            .then(response => {
                if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
                return response.json();
            })
            .then(categories => {
                const categorySelect = document.getElementById('categoryId');
                let options = '<option value="">Select Category</option>';
                categories.forEach(category => {
                    options += `<option value="${category.categoryId}">${category.name}</option>`;
                });
                categorySelect.innerHTML = options;
            })
            .catch(error => {
                console.error('Error loading categories:', error);
                alert('Failed to load categories. Please refresh the page.');
            });
    }

    // Load equipment
    function loadEquipmentData() {
        fetch(`${API_BASE_URL}/api/equipment`, {
            method: "GET",
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
            },
        })
            .then(response => {
                if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
                return response.json();
            })
            .then(equipment => {
                const tableBody = document.getElementById('equipmentTableBody');
                tableBody.innerHTML = '';
                equipment.forEach(item => {
                    tableBody.innerHTML += `
                        <tr>
                            <td>${item.equipmentId}</td>
                            <td>${item.name}</td>
                            <td>${item.rentalPricePerDay}</td>
                            <td>${item.stock}</td>
                           
                            <td>
                                <button class="btn btn-sm btn-primary" onclick="editEquipment(${item.equipmentId})">Edit</button>
                            </td>
                        </tr>
                    `;
                });
            })
            .catch(error => {
                console.error('Error loading equipment:', error);
            });
    }

    // Create equipment
    function createEquipment(equipmentData) {
        return fetch(`${API_BASE_URL}/api/equipment`, {
            method: 'POST',
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(equipmentData)
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(errorData => {
                        throw new Error(`Failed to create equipment: ${errorData}`);
                    });
                }
                return response.json();
            });
    }

    // Update equipment
    function updateEquipment(id, equipmentData) {
        return fetch(`${API_BASE_URL}/api/equipment/${id}`, {
            method: 'PUT',
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(equipmentData)
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(errorData => {
                        throw new Error(`Failed to update equipment: ${errorData}`);
                    });
                }
                return response.json();
            });
    }

    // Edit equipment (populate form)
    window.editEquipment = function (id) {
        fetch(`${API_BASE_URL}/api/equipment/${id}`, {
            method: "GET",
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
            },
        })
            .then(response => {
                if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
                return response.json();
            })
            .then(equipment => {
                document.getElementById('equipmentId').value = equipment.equipmentId;
                document.getElementById('name').value = equipment.name;
                document.getElementById('rentalPricePerDay').value = equipment.rentalPricePerDay;
                document.getElementById('stock').value = equipment.stock;
                document.getElementById('categoryId').value = equipment.categories.categoryId;
            })
            .catch(error => {
                console.error('Error loading equipment details:', error);
            });
    };

    // Reset form
    function resetForm() {
        document.getElementById('equipmentForm').reset();
        document.getElementById('equipmentId').value = '';
    }

    // Initial calls
    loadEquipmentData();
    loadCategories();
})();
