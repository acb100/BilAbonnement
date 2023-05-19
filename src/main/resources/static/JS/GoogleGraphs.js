google.charts.load("current", { packages: ['corechart'] });
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    var unusedCarRows = parseInt(document.getElementById('unusedCarRows').value);
    var usedCarRows = parseInt(document.getElementById('usedCarRows').value);
    var data = google.visualization.arrayToDataTable([
        ["Element", "Mængde", { role: "style" }],
        ["Biler på Lager", unusedCarRows, "gold"],
        ["Brugte Biler", usedCarRows, "silver"],
    ]);

    var view = new google.visualization.DataView(data);
    view.setColumns([
        0,
        1,
        {
            calc: "stringify",
            sourceColumn: 1,
            type: "string",
            role: "annotation",
        },
        2,
    ]);

    var options = {
        title: "Status på Biler",
        width: 600,
        height: 400,
        bar: { groupWidth: "95%" },
        legend: { position: "none" },
    };
    var chart = new google.visualization.ColumnChart(
        document.getElementById("columnchart_cars")
    );
    chart.draw(view, options);

    var allCarsJson = document.getElementById('allCars').value;
    var allCars = JSON.parse(allCarsJson);

    var carData = [['Bil Modeller', 'Mængde']];
    // Add car model data to the array
    allCars.forEach(function (car) {
        var model = car.model_name;
        var quantity = car.model_count;
        carData.push([model, quantity]);
    });

    var data = google.visualization.arrayToDataTable(carData);

    var options = {
        title: 'Car Models'
    };

    var chart = new google.visualization.PieChart(document.getElementById('piechart'));

    chart.draw(data, options);
}