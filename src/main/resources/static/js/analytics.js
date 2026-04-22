
function buildChart(id, data, type) {

    new Chart(document.getElementById(id), {
        type: type,
        data: {
            labels: Object.keys(data),
            datasets: [{
                label: id,
                data: Object.values(data)
            }]
        }
    });
}

buildChart("salesChart", salesData, "line");
buildChart("productChart", productData, "bar");
buildChart("categoryChart", categoryData, "pie");
