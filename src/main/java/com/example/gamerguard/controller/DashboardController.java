package com.example.gamerguard.controller;

import com.example.gamerguard.HelloApplication;
import com.example.gamerguard.model.DatabaseConnection;
import com.example.gamerguard.other.SessionInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    public Text task_display_one;
    @FXML
    public Text task_display_two;
    @FXML
    public Text task_display_three;
    @FXML
    public Text task_display_four;
    @FXML
    public Text task_display_five;

    @FXML
    private TextField taskTextField;

    @FXML
    private LineChart<String, Number> playTimeChart;

    @FXML
    private TextArea productivityTextArea;

    @FXML
    private TextArea gameAppsTextArea;

    @FXML
    private TextArea distractionTextArea;

    @FXML
    private Button settingsButton;

    @FXML
    public Button gamesButton;

    public Button profileButton;


    private XYChart.Series<String, Number> averageSeries;

    //Line Chart
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

        List<String> tasks = fetchTasks(); // Fetch tasks from some data source

        if (!tasks.isEmpty()) {
            // Display tasks in the task_display elements
            if (tasks.size() >= 1) {
                task_display_one.setText(tasks.get(0));
            }
            if (tasks.size() >= 2) {
                task_display_two.setText(tasks.get(1));
            }
            if (tasks.size() >= 3) {
                task_display_three.setText(tasks.get(2));
            }
            if (tasks.size() >= 4) {
                task_display_four.setText(tasks.get(3));
            }
            if (tasks.size() >= 5) {
                task_display_five.setText(tasks.get(4));
            }
        } else {
            // If no tasks are available, display a default message
            task_display_one.setText("Add a task");
        }
    }

    public List<String> fetchTasks() {
        // Implement the logic to fetch tasks from some data source
        List<String> tasks = new ArrayList<>();

        try {
            Connection connectDB = DatabaseConnection.getInstance();
            int userId = SessionInfo.getUserId();
            String sql = "SELECT task FROM todolist WHERE id_account = " + userId; // Assuming you have a table named todolist with a task column
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Process the result set and add tasks to the list
            while (resultSet.next()) {
                tasks.add(resultSet.getString("task"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        // Add more tasks as needed
        return tasks;
    }

    //Insert data into database

    public class PreparedStatementExample {
        public static void main(String[] args) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
                String sql = "INSERT INTO todolist (task, id_account, task_id) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);

                // Set parameter values
                statement.setString(1, "value1");
                statement.setInt(2, 123);

                // Execute the statement
                statement.executeUpdate();

                // Close resources
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // To do list code

    @FXML
    public void createToDoList(ActionEvent event) throws SQLException {
        Connection connectDB = DatabaseConnection.getInstance();
        int userId = SessionInfo.getUserId();
        String task = taskTextField.getText();

        // Use a prepared statement to insert data into the database
        String insertSql = "INSERT INTO todolist (task, id_account) VALUES (?, ?)";
        String sql = "SELECT * FROM user_account WHERE account_id = ?";

        try {
            try (PreparedStatement statement = connectDB.prepareStatement(sql)) {
                statement.setInt(1, userId); // Replace 1 with the appropriate user_id
                ResultSet queryResult = statement.executeQuery();
            }

            // Prepare and execute the insert statement for the todolist table
            try (PreparedStatement sm = connectDB.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                // Set the parameter values for task and account_id
                sm.setString(1, task);
                sm.setInt(2, userId);

                // Execute the statement
                sm.executeUpdate();

                // Retrieve the generated task_id
                ResultSet generatedKeys = sm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int taskId = generatedKeys.getInt(1);
                    // Shift the existing tasks down
                    task_display_five.setText(task_display_four.getText());
                    task_display_four.setText(task_display_three.getText());
                    task_display_three.setText(task_display_two.getText());
                    task_display_two.setText(task_display_one.getText());
                    // Update the first task_display element with the new task
                    task_display_one.setText(task);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Delete all tasks
    @FXML
    private void deleteAllTasks(ActionEvent event) {
        try {
            Connection connectDB = DatabaseConnection.getInstance();
            String sql = "DELETE FROM todolist";
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(sql);

            // Clear task_display elements
            task_display_one.setText("");
            task_display_two.setText("");
            task_display_three.setText("");
            task_display_four.setText("");
            task_display_five.setText("");

            task_display_one.setText("Add a task");

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    // Delete only one specific task

    @FXML
    private void deleteTaskOne(javafx.scene.input.MouseEvent mouseEvent) {
        deleteTask(1);
    }

    @FXML
    private void deleteTaskTwo(javafx.scene.input.MouseEvent mouseEvent) {
        deleteTask(2);
    }

    @FXML
    private void deleteTaskThree(javafx.scene.input.MouseEvent mouseEvent) {
        deleteTask(3);
    }

    @FXML
    private void deleteTaskFour(javafx.scene.input.MouseEvent mouseEvent) {
        deleteTask(4);
    }

    @FXML
    private void deleteTaskFive(javafx.scene.input.MouseEvent mouseEvent) {
        deleteTask(5);
    }

    private void deleteTask(int taskId) {
        try {
            Connection connectDB = DatabaseConnection.getInstance();
            String sql = "DELETE FROM todolist WHERE task_id = ?";
            PreparedStatement statement = connectDB.prepareStatement(sql);
            statement.setInt(1, taskId);
            statement.executeUpdate();

            // Clear the corresponding task_display element
            switch (taskId) {
                case 1:
                    task_display_one.setText("");
                    break;
                case 2:
                    task_display_two.setText("");
                    break;
                case 3:
                    task_display_three.setText("");
                    break;
                case 4:
                    task_display_four.setText("");
                    break;
                case 5:
                    task_display_five.setText("");
                    break;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
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

    public void settingsButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/Settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        Stage stage = (Stage) settingsButton.getScene().getWindow();
        stage.setScene(scene);
    }

    public void profileButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/profile-settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        Stage stage = (Stage) profileButton.getScene().getWindow();
        stage.setScene(scene);
    }

    public void gamesButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("display-games.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        Stage stage = (Stage) profileButton.getScene().getWindow();
        stage.setScene(scene);
    }
}