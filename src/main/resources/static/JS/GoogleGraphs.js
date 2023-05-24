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

    var columnchart_options = {
        title: "Status på Biler",
        bar: { groupWidth: "95%" },
        legend: { position: "none" },
        backgroundColor: 'whitesmoke'
    };
    var columnchart = new google.visualization.ColumnChart(
        document.getElementById("columnchart_cars")
    );
    columnchart.draw(view, columnchart_options);

    var allCarsJson = document.getElementById('allCars').value;
    var allCars = JSON.parse(allCarsJson);

    var carData = [['Bil Modeller', 'Mængde']];
    // Add car model data to the array
    allCars.forEach(function (car) {
        var model = car.model_name;
        var quantity = car.model_count;
        carData.push([model, quantity]);
    });

    var pieData = google.visualization.arrayToDataTable(carData);

    var piechart_options = {
        title: 'Alle Bilmodeller',
        backgroundColor: 'whitesmoke'
    };

    var piechart = new google.visualization.PieChart(document.getElementById('piechart'));

    piechart.draw(pieData, piechart_options);
}
