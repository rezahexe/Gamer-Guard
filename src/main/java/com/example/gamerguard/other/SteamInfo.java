package com.example.gamerguard.other;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for retrieving information from Steam API.
 */
public class SteamInfo {

    /**
     * Retrieves a map of all Steam game IDs to their respective names.
     *
     * @param apiKey the Steam API key
     * @return a map where the key is the game ID and the value is the game name
     * @throws IOException if there is an issue with the API request
     */
    private static Map<Integer, String> getGameNames(String apiKey) throws IOException {
        String url = "http://api.steampowered.com/ISteamApps/GetAppList/v2"; // GetAppList API endpoint url
        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        // Check if the request was successful
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
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
            throw new IOException("Error: HTTP " + responseCode);
        }
    }

    /**
     * Retrieves a list of all games owned by a Steam user.
     *
     * @param apiKey the Steam API key
     * @param steamId the Steam ID of the user
     * @return a list of Game objects representing the user's owned games
     */
    public static List<Game> getAllSteamGames(String apiKey, String steamId) {
        List<Game> gamesList = new ArrayList<>();

        String url = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=" + apiKey + "&steamid=" + steamId + "&format=json";

        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            // Check if the request was successful
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                // Read the response line by line
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the JSON response
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(response.toString());
                JsonNode gamesNode = rootNode.path("response").path("games");

                // Map of app IDs to game names
                Map<Integer, String> appNamesMap = getGameNames(apiKey);

                // Display the game names
                for (JsonNode gameNode : gamesNode) {
                    int appId = gameNode.path("appid").asInt();
                    int playtimeForever = gameNode.path("playtime_forever").asInt();
                    String gameName = appNamesMap.getOrDefault(appId, "Unknown Game");
                    if (!gameName.equals("Unknown Game")) {
                        gamesList.add(new Game(gameName, playtimeForever));
                    }
                }
            } else {
                System.out.println("Error: HTTP " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gamesList;
    }

    /**
     * Class representing a game owned by a Steam user.
     */
    public static class Game {
        private String name;
        private int playtime;

        /**
         * Constructor for creating a Game object.
         *
         * @param name the name of the game
         * @param playtime the total playtime of the game in minutes
         */
        public Game(String name, int playtime) {
            this.name = name;
            this.playtime = playtime;
        }

        /**
         * Gets the name of the game.
         *
         * @return the name of the game
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the total playtime of the game in minutes.
         *
         * @return the total playtime of the game in minutes
         */
        public int getPlaytime() {
            return playtime;
        }
    }
}
