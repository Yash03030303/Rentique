
  currentPage = 0;
  rowsPerPage = 5;
  currentSortField = "returnDate";
  currentSortAsc = true;
  totalPages = 0;

  async function fetchCompletedReturns() {
    itemCondition = document.getElementById("searchInput").value;
    startDate = document.getElementById("startDate").value;
    endDate = document.getElementById("endDate").value;

    sortDir = currentSortAsc ? "asc" : "desc";
    var sortBy = currentSortField;

    url = new URL("http://localhost:8080/api/returns/search");
    url.searchParams.append("itemCondition", itemCondition || "");
    url.searchParams.append("startDate", startDate || "1900-01-01");
    url.searchParams.append("endDate", endDate || "2100-12-31");
    url.searchParams.append("page", currentPage);
    url.searchParams.append("size", rowsPerPage);
    url.searchParams.append("sortBy", sortBy);
    url.searchParams.append("sortDir", sortDir);

    try {
      response = await fetch(url.toString(), {
        headers: {
          Authorization: getAuthorization(),
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) throw new Error("Failed to fetch data");

      data = await response.json();
      renderTable(data.content);
      totalPages = data.totalPages;
      renderPagination();
    } catch (error) {
      console.error("Error fetching return data:", error);
      document.getElementById("errorMessage").classList.remove("d-none");
    }
  }

  function renderTable(data) {
    tableBody = document.getElementById("returnsTableBody");
    tableBody.innerHTML = "";

    data.forEach((entry) => {
      row = document.createElement("tr");
      row.innerHTML = `
        <td>${entry.returnId}</td>
        <td>${entry.returnDate}</td>
        <td>${entry.itemCondition}</td>
        <td>₹${entry.lateFee.toFixed(2)}</td>
      `;

      switch (entry.itemCondition.toLowerCase()) {
        case "good":
          row.classList.add("table-success");
          break;
        case "damaged":
          row.classList.add("table-warning");
          break;
        case "broken":
          row.classList.add("table-danger");
          break;
      }

      tableBody.appendChild(row);
    });
  }

  function renderPagination() {
    pagination = document.getElementById("paginationControls");
    pagination.innerHTML = "";

    for (let i = 0; i < totalPages; i++) {
      li = document.createElement("li");
      li.className = `page-item ${i === currentPage ? "active" : ""}`;
      li.innerHTML = `<a class="page-link" href="#">${i + 1}</a>`;
      li.addEventListener("click", function (e) {
        e.preventDefault();
        currentPage = i;
        fetchCompletedReturns();
      });
      pagination.appendChild(li);
    }
  }

  function sortBy(field) {
    if (currentSortField === field) {
      currentSortAsc = !currentSortAsc;
    } else {
      currentSortField = field;
      currentSortAsc = true;
    }
    fetchCompletedReturns();
  }

  fetchCompletedReturns();
