package com.example.gamerguard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
//import java.security.PublicKey;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HelloApplication extends Application {
    public static final String TITLE = "Gamer Guard";
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
        stage.initStyle(StageStyle.DECORATED); // Change to "UNDECORATED" to remove title bar.
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }


    // Method to fetch the mapping of app IDs to game names
    private static Map<Integer, String> getAppNamesMap(String apiKey) throws IOException {
        // URL for the GetAppList API endpoint
        String url = "http://api.steampowered.com/ISteamApps/GetAppList/v2";

        // Create a URL object
        URL apiUrl = new URL(url);

        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

        // Set request method
        connection.setRequestMethod("GET");

        // Get the response code
        int responseCode = connection.getResponseCode();

        // Check if the request was successful (HTTP status code 200)
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Create a BufferedReader to read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            // Read the response line by line
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Close the BufferedReader
            reader.close();

            // Parse the JSON response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.toString());
            JsonNode appList = rootNode.path("applist").path("apps");

            // Create a map of app IDs to game names
            Map<Integer, String> appNamesMap = new HashMap<>();
            for (JsonNode appNode : appList) {
                int appId = appNode.path("appid").asInt();
                String appName = appNode.path("name").asText();
                appNamesMap.put(appId, appName);
            }

            return appNamesMap;
        } else {
            // If the request was not successful, print the error message
            throw new IOException("Error: HTTP " + responseCode);
        }
    }

    public static void main(String[] args) {
        // Steam API key
        String apiKey = "52860EB63B04841C64DD2594B2EE46CB";

        // Steam user ID (64-bit Steam ID)
        String steamId = "76561198324969960";

        // Construct the URL for the GetOwnedGames API endpoint
        String url = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=" + apiKey + "&steamid=" + steamId + "&format=json";

        try {
            // Create a URL object
            URL apiUrl = new URL(url);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

            // Set request method
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            // Check if the request was successful (HTTP status code 200)
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Create a BufferedReader to read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                // Read the response line by line
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // Close the BufferedReader
                reader.close();

                // Parse the JSON response
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(response.toString());
                JsonNode gamesNode = rootNode.path("response").path("games");

                // Map of app IDs to game names
                Map<Integer, String> appNamesMap = getAppNamesMap(apiKey);

                // Display the game names
                for (JsonNode gameNode : gamesNode) {
                    int appId = gameNode.path("appid").asInt();
                    int playtimeForever = gameNode.path("playtime_forever").asInt();
                    String gameName = appNamesMap.getOrDefault(appId, "Unknown Game");
                    System.out.println("Game Name: " + gameName + ", Playtime: " + playtimeForever);
                }
            } else {
                // If the request was not successful, print the error message
                System.out.println("Error: HTTP " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        launch();

    }

}