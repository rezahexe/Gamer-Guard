package com.example.gamerguard.controller;

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
import java.util.*;

import javafx.scene.control.TableColumn;

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
        // Your Steam API key and Steam user ID
        String apiKey = "52860EB63B04841C64DD2594B2EE46CB";
        String steamId = "76561198434881039";

        SteamInfo steamInfo = new SteamInfo();

        // Call getAllSteamGames method to retrieve the game list
        List<SteamInfo.Game> gamesList = steamInfo.getAllSteamGames(apiKey, steamId);

//        for (SteamInfo.Game game : gamesList) {
//            System.out.println("Game Name: " + game.getName() + ", Playtime: " + game.getPlaytime());
//        }

        // Create ObservableList of Map<String, Object>
        ObservableList<Map<String, Object>> data = FXCollections.observableArrayList();
        //Order according to the game max time
        gamesList.sort(Comparator.comparingInt(SteamInfo.Game::getPlaytime).reversed());

        for (int i = 0; i < gamesList.size(); i++) {
            SteamInfo.Game game = gamesList.get(i);

            Map<String, Object> rowData = new HashMap<>();
            rowData.put("Top", i+1);
            rowData.put("Name", game.getName());
            rowData.put("Hours This week", 1);
            rowData.put("Hours Total", game.getPlaytime());
            rowData.put("Status", "healthy");
            // Add the row data to the ObservableList
            data.add(rowData);
            // Populate the TableView with data
            tableView.setItems(data);
        }

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