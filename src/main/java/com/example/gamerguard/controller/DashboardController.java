package com.example.gamerguard.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;

public class DashboardController {

    @FXML
    private LineChart<String, Number> playTimeChart;

    @FXML
    private TextArea productivityTextArea;

    @FXML
    private TextArea gameAppsTextArea;

    @FXML
    private TextArea distractionTextArea;

    private XYChart.Series<String, Number> averageSeries;

    //Line Chart
    public void initialize() {
        // Initialize the chart with data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("This Week");

        // Add sample data (replace this with your actual data)
        series.getData().add(new XYChart.Data<>("Mon", 3.5));
        series.getData().add(new XYChart.Data<>("Tue", 2.5));
        series.getData().add(new XYChart.Data<>("Wed", 3));
        series.getData().add(new XYChart.Data<>("Thu", 4));
        series.getData().add(new XYChart.Data<>("Fri", 6.6));
        series.getData().add(new XYChart.Data<>("Sat", 6));
        series.getData().add(new XYChart.Data<>("Sun", 4));

        // Create a new series for average playtime per day
        averageSeries = new XYChart.Series<>();
        averageSeries.setName("Goal Time");

        // Add sample average data (replace this with your actual data)
        averageSeries.getData().add(new XYChart.Data<>("Mon", 2));
        averageSeries.getData().add(new XYChart.Data<>("Tue", 2));
        averageSeries.getData().add(new XYChart.Data<>("Wed", 3));
        averageSeries.getData().add(new XYChart.Data<>("Thu", 1));
        averageSeries.getData().add(new XYChart.Data<>("Fri", 4));
        averageSeries.getData().add(new XYChart.Data<>("Sat", 5));
        averageSeries.getData().add(new XYChart.Data<>("Sun", 3));

        // Set the data to the chart
        playTimeChart.getData().addAll(series, averageSeries);
    }

    @FXML
    protected void onHelloButtonClick() {
        // Update chart data when button is clicked (for demonstration)
        XYChart.Series<String, Number> series = playTimeChart.getData().get(0);
        XYChart.Series<String, Number> averageSeries = playTimeChart.getData().get(1);

        // Update sample data (replace this with your actual data or logic)
        series.getData().forEach(data -> data.setYValue(data.getYValue().intValue() + 1));
        averageSeries.getData().forEach(data -> data.setYValue(data.getYValue().intValue() + 1));
    }

    @FXML
    protected void onResetGoalButtonClick() {
        // Reset text areas to "0 hrs/week"
        productivityTextArea.setText("0 hrs/week");
        gameAppsTextArea.setText("0 hrs/week");
        distractionTextArea.setText("0 hrs/week");

        // Reset average series data to zero
        averageSeries.getData().forEach(data -> data.setYValue(0));
    }
}