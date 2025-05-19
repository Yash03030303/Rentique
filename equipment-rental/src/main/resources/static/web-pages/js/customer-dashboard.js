window.onload = function () {
    const token = localStorage.getItem('token'); // If using JWT auth
    const userId = getUserID(token); // Assuming userId is stored in localStorage




    fetch(`http://localhost:8080/api/dashboard/customer/${userId}`, {
        headers: token ? { 'Authorization': 'Bearer ' + token } : {}
    })
    .then(response => {
        if (!response.ok) throw new Error('Failed to fetch dashboard data');
        return response.json();
    })
    .then(data => {
        // Update card numbers
        document.getElementById('activeRentals').textContent = data.activeRentals ?? 0;
        document.getElementById('overdueReturns').textContent = data.overdueReturns ?? 0;
        document.getElementById('totalRentals').textContent = data.totalRentals ?? 0;
        console.log(data);
        // Pie Chart - Rental Categories
        const pieLabels = data.rentalCategories ? Object.keys(data.rentalCategories) : [];
        const pieValues = data.rentalCategories ? Object.values(data.rentalCategories) : [];
        const pieColors = [
            '#004D61', '#822659', '#3E5641', '#F0F0F0', '#b00020', '#FFB300', '#1976D2',
            '#59a14f', '#edc949', '#af7aa1', '#ff9da7', '#9c755f'
        ];

        if (window.customerPieChartInstance) {
            window.customerPieChartInstance.destroy();
        }
        const pieCtx = document.getElementById('customerPieChart').getContext('2d');
        window.customerPieChartInstance = new Chart(pieCtx, {
            type: 'pie',
            data: {
                labels: pieLabels,
                datasets: [{
                    data: pieValues,
                    backgroundColor: pieColors.slice(0, pieLabels.length),
                    borderColor: '#fff',
                    borderWidth: 2
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { position: 'bottom' }
                }
            }
        });

        // Bar Chart - Monthly Rentals
        const barLabels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        const barData = data.monthlyRentals && Array.isArray(data.monthlyRentals) ? data.monthlyRentals : Array(12).fill(0);

        if (window.customerBarChartInstance) {
            window.customerBarChartInstance.destroy();
        }
        const barCtx = document.getElementById('customerBarChart').getContext('2d');
        window.customerBarChartInstance = new Chart(barCtx, {
            type: 'bar',
            data: {
                labels: barLabels,
                datasets: [{
                    label: 'My Rentals',
                    data: barData,
                    backgroundColor: '#004D61'
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: { beginAtZero: true }
                },
                plugins: { legend: { display: false } }
            }
        });
    })
    .catch(error => {
        console.error('Dashboard error:', error);
        alert('Failed to load dashboard data');
    });
};