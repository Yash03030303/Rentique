// supplier-dashboard.js

   


let pieChart, barChart;

async function initDashboard() {
    try {
        // Check user type
        const userType = getUserType();
        if(userType !== 'SUPPLIER') {
            window.location.href = 'login.html';
            return;
        }

        // Fetch dashboard data
        const response = await fetch('http://localhost:8080/api/dashboard', {
            headers: {
                'Authorization': getAuthorization()
            }
        });

        if(!response.ok) {
            if(response.status === 401) {
                logout();
            }
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();

        // Update cards
        document.querySelector('.bg-primary h2').textContent = data.totalEquipment;
        document.querySelector('.bg-success h2').textContent = data.activeRentals;
        
        // Hide pending requests card
        document.querySelector('.bg-warning').style.display = 'none';

        // Initialize charts
        initPieChart(data.equipmentDistribution);
        initBarChart(data.monthlyRentals);

    } catch (error) {
        console.error('Dashboard init error:', error);
        document.getElementById('main-content').innerHTML = `
            <div class="alert alert-danger mt-4">
                Failed to load dashboard data: ${error.message}
            </div>
        `;
    }
}

function initPieChart(distributionData) {
    const ctx = document.getElementById('supplierPieChart').getContext('2d');
    
    // Destroy existing chart if exists
    if(pieChart) pieChart.destroy();

    pieChart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: Object.keys(distributionData),
            datasets: [{
                data: Object.values(distributionData),
                backgroundColor: [
                    '#4e73df', '#1cc88a', '#36b9cc', '#f6c23e',
                    '#e74a3b', '#858796', '#5a5c69', '#3a3b45'
                ],
                hoverOffset: 4
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'bottom',
                    labels: {
                        padding: 20,
                        font: {
                            size: 14
                        }
                    }
                }
            }
        }
    });
}

function initBarChart(monthlyData) {
    const ctx = document.getElementById('supplierBarChart').getContext('2d');
    
    // Destroy existing chart if exists
    if(barChart) barChart.destroy();

    // Sort months chronologically
    const sortedMonths = Object.keys(monthlyData).sort();
    const sortedValues = sortedMonths.map(month => monthlyData[month]);

    barChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: sortedMonths,
            datasets: [{
                label: 'Number of Rentals',
                data: sortedValues,
                backgroundColor: '#4e73df',
                borderColor: '#4e73df',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        stepSize: 1
                    }
                }
            },
            plugins: {
                legend: {
                    display: false
                }
            }
        }
    });
}

// Handle window resize to maintain chart aspect ratio
window.addEventListener('resize', function() {
    if(pieChart) pieChart.resize();
    if(barChart) barChart.resize();
});

 initDashboard();