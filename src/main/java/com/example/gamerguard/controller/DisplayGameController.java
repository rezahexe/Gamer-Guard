package com.example.gamerguard.controller;

import com.example.gamerguard.model.DatabaseConnection;
import com.example.gamerguard.other.SessionInfo;
import com.example.gamerguard.other.SteamInfo;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import javafx.scene.control.TableColumn;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class DisplayGameController implements Initializable {
    @FXML
    private Button infoButton;

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



    public  void initialize (URL url, ResourceBundle resourceBundle) {
        System.out.println("Meoow emwo");
        //int userId = 15;
        int userId = SessionInfo.getUserId();
        //--------------------------------------------------------------------------------------------------------------
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);
        System.out.println("Current date (formatted): " + formattedDate);

        // Your Steam API key and Steam user ID-------------------------------------------------------------------------
        String apiKey = "52860EB63B04841C64DD2594B2EE46CB";
        Connection connectDB = DatabaseConnection.getInstance();
        String query = "SELECT user_steamId FROM user_steam WHERE user_id = ?";
        String steamId = null;

        try (PreparedStatement stmt = connectDB.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    steamId = rs.getString("user_steamId");

                    //--------------------------------------------------------------------------------------------------
                    SteamInfo steamInfo = new SteamInfo();

                    // Call getAllSteamGames method to retrieve the game list
                    List<SteamInfo.Game> gamesList = steamInfo.getAllSteamGames(apiKey, steamId);

                    // Create ObservableList of Map<String, Object>
                    ObservableList<Map<String, Object>> data = FXCollections.observableArrayList();
                    //Order according to the game max time
                    gamesList.sort(Comparator.comparingInt(SteamInfo.Game::getPlaytime).reversed());

                    //TESTINGUEDGIEUGDHIU-------------------------------------------------------------------------------------------

                    //Connection connectDB = DatabaseConnection.getInstance();
                    String queryGame = "SELECT * FROM user_gametime WHERE user_id = ? AND game_name = ?";

                    for (int i = 0; i < gamesList.size(); i++) {
                        SteamInfo.Game game = gamesList.get(i);

                        int hour = 0;
                        String status = "X";
                        try (PreparedStatement stmt2 = connectDB.prepareStatement(queryGame)) {
                            stmt2.setString(1, String.valueOf(userId)); // Set the user_id parameter
                            stmt2.setString(2, game.getName()); // Set the game_name parameter
                            try (ResultSet rs2 = stmt2.executeQuery()) {
                                if (rs.next()) { //If game exists
                                    String prevDate = rs2.getString("recent_day_save");
                                    int prevTime = rs2.getInt("game_prevtime");

                                    LocalDate date1 = LocalDate.parse(formattedDate);
                                    LocalDate date2 = LocalDate.parse(prevDate);

                                    // Calculate the difference in days between the two dates
                                    long daysDifference = ChronoUnit.DAYS.between(date2, date1);
                                    if (daysDifference > 7){
                                        hour = game.getPlaytime()-prevTime;
                                    }
                                    else{
                                        hour += game.getPlaytime()-prevTime;
                                    }

                                    //>:3 Update previous total time and time recorded?
                                    int newPrevTime = game.getPlaytime();
                                    updateGamePrevTime(connectDB, String.valueOf(userId), game.getName(), newPrevTime, currentDate.format(formatter));
                                } else {
                                    // If the user does not have a game record, insert a new row
                                    insertNewGameRecord(connectDB, String.valueOf(userId), game.getName(), game.getPlaytime(), hour, String.valueOf(currentDate));
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        if (hour > 14) {
                            status = "Excessive";
                        } else if (hour > 0 && hour <= 14) {
                            status = "Healthy";
                        }


                        Map<String, Object> rowData = new HashMap<>();
                        rowData.put("Top", i+1);
                        rowData.put("Name", game.getName());
                        rowData.put("Hours This week", hour);
                        rowData.put("Hours Total", game.getPlaytime());
                        rowData.put("Status", status);
                        // Add the row data to the ObservableList
                        data.add(rowData);
                    }

                    // Populate the TableView with data
                    tableView.setItems(data);

                    // Set cell value factories for each column
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
                    //--------------------------------------------------------------------------------------------------
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (steamId == null) {
            // Handle the case where the Steam ID is not found for the given user ID
            System.out.println(">:3 Lame, you dont have steam lolol");
        }
    }


    private void updateGamePrevTime(Connection conn, String userId, String gameName, int newPrevTime, String newRecentDaySaved) throws SQLException {
        String updateQuery = "UPDATE user_gametime SET game_prevtime = ?, recent_day_save = ? WHERE user_id = ? AND game_name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setInt(1, newPrevTime);
            stmt.setString(2, newRecentDaySaved);
            stmt.setString(3, userId);
            stmt.setString(4, gameName);
            stmt.executeUpdate();
            System.out.println(">:3 Updated table");
        }
    }

    private void insertNewGameRecord(Connection conn, String userId, String gameName, int gamePrevTime, int hour, String currentDate) throws SQLException {
        String insertQuery = "INSERT INTO user_gametime (user_id, game_name, game_prevtime, game_hour, recent_day_save) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setString(1, userId);
            stmt.setString(2, gameName);
            stmt.setInt(3, gamePrevTime);
            stmt.setInt(4, hour);
            stmt.setString(5, currentDate);
            stmt.executeUpdate();
            System.out.println(">:3 Added table");
        }
    }


    @FXML
    void infoButtonOnAction(ActionEvent event) {

    }

    @FXML
    void profileButtonOnAction(ActionEvent event) {

    }

    @FXML
    void settingsButtonOnAction(ActionEvent event) {

    }

}