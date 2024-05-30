package com.example.gamerguard.controller;

import com.example.gamerguard.HelloApplication;
import com.example.gamerguard.model.DatabaseConnection;
import com.example.gamerguard.other.SessionInfo;
import com.example.gamerguard.other.SteamInfo;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalDate;


/**
 * Controller class for displaying game information in the UI.
 * Handles interactions and updates related to the game display.
 *
 * @author Serene Coders
 * @version 1.0.0
 */
public class DisplayGameController implements Initializable {
    @FXML
    private Button infoButton;
    @FXML
    private ImageView BackButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button settingsButton;
    @FXML
    private TableView<Map<String, Object>> tableView;
    @FXML
    private TableColumn<Map<String, Object>, Integer> colTop;
    @FXML
    private TableColumn<Map<String, Object>, String> colName;
    @FXML
    private TableColumn<Map<String, Object>, Integer> colHoursWeek;
    @FXML
    private TableColumn<Map<String, Object>, Integer> colHoursTotal;
    @FXML
    private TableColumn<Map<String, Object>, String> colStatus;
    public Text text_display_username;


    /**
     * {@inheritDoc}
     *
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        String userName = SessionInfo.getUserName();
        text_display_username.setText(userName);

        //Table view message if there are no games, invalid Steam ID, or no Steam ID
        Label placeholderLabel = new Label("Add a valid steam ID in Profile Settings");
        tableView.setPlaceholder(placeholderLabel);

        int userId = SessionInfo.getUserId();

        File logoFile0 = new File("Images/button_back.png");
        Image logoImage0 = new Image(logoFile0.toURI().toString());
        BackButton.setImage(logoImage0);

        //Get current date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);

        String apiKey = "52860EB63B04841C64DD2594B2EE46CB";
        Connection connectDB = DatabaseConnection.getInstance();

        //Get Steam information based on user's steam ID
        String query = "SELECT user_steamId FROM user_steam WHERE user_id = ?";
        String steamId = null;
        try (PreparedStatement stmt = connectDB.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    steamId = rs.getString("user_steamId");
                    SteamInfo steamInfo = new SteamInfo();

                    // getAllSteamGames method to retrieve the game list
                    List<SteamInfo.Game> gamesList = steamInfo.getAllSteamGames(apiKey, steamId);

                    // Create ObservableList of Map<String, Object>
                    ObservableList<Map<String, Object>> data = FXCollections.observableArrayList();
                    //Order of the records in table according to the game total play time
                    gamesList.sort(Comparator.comparingInt(SteamInfo.Game::getPlaytime).reversed());

                    String queryGame = "SELECT * FROM user_gametime WHERE user_id = ? AND game_name = ?";
                    for (int i = 0; i < gamesList.size(); i++) {
                        SteamInfo.Game game = gamesList.get(i);
                        if (game.getPlaytime() == 0) {
                            continue;
                        }
                        int hour = 0;
                        String status = "X"; //Default status if there are no hours played
                        try (PreparedStatement stmt2 = connectDB.prepareStatement(queryGame)) { //Check if game and user ID already exists
                            stmt2.setString(1, String.valueOf(userId)); // Set parameters
                            stmt2.setString(2, game.getName());
                            try (ResultSet rs2 = stmt2.executeQuery()) {
                                if (rs2.next()) {
                                    //If game already exists in database, calculate the hours played in the week comparing last total playtime
                                    String prevDate = rs2.getString("recent_day_save");
                                    int prevTime = rs2.getInt("game_prevtime");
                                    int gameHour = rs2.getInt("game_hour");
                                    LocalDate date1 = LocalDate.parse(formattedDate);
                                    LocalDate date2 = LocalDate.parse(prevDate);
                                    long daysDifference = ChronoUnit.DAYS.between(date2, date1);
                                    if (daysDifference > 7){
                                        hour = game.getPlaytime()-prevTime;
                                    }
                                    else{
                                        hour = gameHour+game.getPlaytime()-prevTime;
                                    }
                                    // Update previous total play time and time recorded
                                    int newPrevTime = game.getPlaytime();
                                    updateGamePrevTime(connectDB, String.valueOf(userId), game.getName(), newPrevTime, currentDate.format(formatter), hour);
                                } else {
                                    // If user does not game recorded, insert a new row
                                    insertNewGameRecord(connectDB, String.valueOf(userId), game.getName(), game.getPlaytime(), hour, String.valueOf(currentDate));
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        // Update display status depending on hours played
                        if (hour > 14*60) {
                            status = "Excessive";
                        } else if (hour > 0 && hour <= 14*60) {
                            status = "Healthy";
                        }
                        // Update data for rows
                        Map<String, Object> rowData = new HashMap<>();
                        rowData.put("Top", i+1);
                        rowData.put("Name", game.getName());
                        rowData.put("Hours This week", hour/60);
                        rowData.put("Hours Total", game.getPlaytime()/60);
                        rowData.put("Status", status);
                        data.add(rowData);
                    }

                    // Display data in table
                    tableView.setItems(data);
                    colTop.setCellValueFactory(cellData -> {
                        Integer value = (Integer) cellData.getValue().get("Top");
                        return new SimpleIntegerProperty(value).asObject();
                    });
                    colName.setCellValueFactory(cellData -> {
                        String value = (String) cellData.getValue().get("Name");
                        return new SimpleStringProperty(value);
                    });
                    colHoursWeek.setCellValueFactory(cellData -> {
                        Integer value = (Integer) cellData.getValue().get("Hours This week");
                        return new SimpleIntegerProperty(value).asObject();
                    });
                    colHoursTotal.setCellValueFactory(cellData -> {
                        Integer value = (Integer) cellData.getValue().get("Hours Total");
                        return new SimpleIntegerProperty(value).asObject();
                    });
                    colStatus.setCellValueFactory(cellData -> {
                        String value = (String) cellData.getValue().get("Status");
                        return new SimpleStringProperty(value);
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Update database record of game for the user
     * @param conn Database connection
     * @param userId User ID
     * @param gameName Name of game
     * @param newPrevTime Total play time of game
     * @param newRecentDaySaved Date of last checked for increase of steam play time
     * @throws SQLException SQL exception
     */
    private void updateGamePrevTime(Connection conn, String userId, String gameName, int newPrevTime, String newRecentDaySaved, int hour) throws SQLException {
        String updateQuery = "UPDATE user_gametime SET game_prevtime = ?, recent_day_save = ?, game_hour = ? WHERE user_id = ? AND game_name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setInt(1, newPrevTime);
            stmt.setString(2, newRecentDaySaved);
            stmt.setInt(3, hour);
            stmt.setString(4, String.valueOf(userId));
            stmt.setString(5, gameName);
            stmt.executeUpdate();
        }
    }


    /**
     * Add database record of game for the user
     * @param conn Database connection
     * @param userId User ID
     * @param gameName Name of game
     * @param gamePrevTime Total play time of game
     * @param hour User's hours played for specific game
     * @param currentDate Current date
     * @throws SQLException SQL exception
     */
    private void insertNewGameRecord(Connection conn, String userId, String gameName, int gamePrevTime, int hour, String currentDate) throws SQLException {
        String insertQuery = "INSERT INTO user_gametime (user_id, game_name, game_prevtime, game_hour, recent_day_save) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setString(1, userId);
            stmt.setString(2, gameName);
            stmt.setInt(3, gamePrevTime);
            stmt.setInt(4, hour);
            stmt.setString(5, currentDate);
            stmt.executeUpdate();
        }
    }


    /**
     * Handles the action when the settings button is clicked.
     *
     * @param event The event that triggered this action.
     * @throws java.io.IOException If an input or output exception occurred
     */
    public void settingsButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/Settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        Stage stage = (Stage) settingsButton.getScene().getWindow();
        stage.setScene(scene);
    }


    /**
     * Handles the action when the profile button is clicked.
     *
     * @param event The event that triggered this action.
     * @throws java.io.IOException If an input or output exception occurred
     */
    public void profileButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/profile-settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        Stage stage = (Stage) profileButton.getScene().getWindow();
        stage.setScene(scene);
    }


    /**
     * Handles the action when the back button is clicked.
     *
     * @param event The event that triggered this action.
     * @throws java.io.IOException If an input or output exception occurred
     */
    @FXML
    public void BackOnAction(MouseEvent event) throws IOException {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }


    /**
     * Handles the action when the info button is clicked.
     *
     * @param event The event that triggered this action.
     */
    public void infoButtonOnAction(ActionEvent event) {
    }
}
