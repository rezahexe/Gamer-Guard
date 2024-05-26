package com.example.gamerguard.controller.Settings;

import com.example.gamerguard.controller.OTPController;
import com.example.gamerguard.model.DatabaseConnection;
import com.example.gamerguard.HelloApplication;
import com.example.gamerguard.other.SessionInfo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ResourceBundle;
import java.net.URL;


/**
 * Controller for handling profile settings.
 */
public class ProfileSettingsController implements Initializable {
    @FXML
    private Button steamUpdateButton;
    public Label btnTXT_change_password;
    public Rectangle btnBG_change_password;
    public Label btnTXT_delete_account;
    public Rectangle btnBG_delete_account;
    public Text text_display_username;
    @FXML
    private ImageView logoImageView;
    @FXML
    private ImageView button_back;
    @FXML
    private ImageView logoImageView1;
    @FXML
    private ImageView logoImageView2;
    @FXML
    private TextField steamTextField;
    @FXML
    private TextField spotifyTextField;


    /**
     * Initializes the controller.
     * Loads the logo images from the specified file path and sets it
     * as the image source. Set event handlers for mouse click events on buttons.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle that contains localized objects for the controller, or null if there is no resource bundle.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File logoFile = new File("Images/defaultprofile.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);

        String userName = SessionInfo.getUserName();
        text_display_username.setText(userName);

        File logoFile0 = new File("Images/button_back.png");
        Image logoImage0 = new Image(logoFile0.toURI().toString());
        button_back.setImage(logoImage0);

        File logoFile1 = new File("Images/icon_steam.png");
        Image logoImage1 = new Image(logoFile1.toURI().toString());
        logoImageView1.setImage(logoImage1);

        File logoFile2 = new File("Images/icon_spotify.png");
        Image logoImage2 = new Image(logoFile2.toURI().toString());
        logoImageView2.setImage(logoImage2);

        btnTXT_change_password.setOnMouseClicked(this::changepasswordButtonOnAction);
        btnBG_change_password.setOnMouseClicked(this::changepasswordButtonOnAction);

        btnTXT_delete_account.setOnMouseClicked(this::deleteButtonOnAction);
        btnBG_delete_account.setOnMouseClicked(this::deleteButtonOnAction);

        //Displays the user's steam ID in text box
        int userId = SessionInfo.getUserId();
        Connection connectDB = DatabaseConnection.getInstance();
        String queryFindSteam = "SELECT user_steamId FROM user_steam WHERE user_id = ?";
        try (PreparedStatement stmt = connectDB.prepareStatement(queryFindSteam)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) { // User have steam Id
                    String userSteamId = rs.getString("user_steamId");
                    steamTextField.setText(userSteamId); // Steam ID found
                } else { //User don't have steam Id
                    steamTextField.setText("");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Opens the dashboard window and closes the current page.
     */
    @FXML
    public void backButtonOnAction() throws IOException {
        Stage stage = (Stage) button_back.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }


    /**
     * Updates or insert new record of user's Steam ID when click update Steam button.
     *
     * @param event ActionEvent triggered by clicking the login button.
     */
    public void steamUpdateButtonOnAction(ActionEvent event) {
        if (!steamTextField.getText().isBlank()) {
            int userId = SessionInfo.getUserId();
            String newSteamId = steamTextField.getText();
            Connection connectDB = DatabaseConnection.getInstance();
            String queryFindSteam = "SELECT user_steamId FROM user_steam WHERE user_id = ?";
            try (PreparedStatement stmt = connectDB.prepareStatement(queryFindSteam)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) { // User have Steam ID (Update)
                        String queryUpdateSteam = "UPDATE user_steam SET user_steamId = ? WHERE user_id = ?";
                        try (PreparedStatement updateStmt = connectDB.prepareStatement(queryUpdateSteam)) {
                            updateStmt.setString(1, newSteamId);
                            updateStmt.setInt(2, userId);
                            updateStmt.executeUpdate();
                        }
                    } else { //No Steam ID (add new record)
                        String queryInsertSteam = "INSERT INTO user_steam (user_id, user_steamId) VALUES (?, ?)";
                        try (PreparedStatement insertStmt = connectDB.prepareStatement(queryInsertSteam)) {
                            insertStmt.setInt(1, userId);
                            insertStmt.setString(2, newSteamId);
                            insertStmt.executeUpdate();
                        }
                        String deletePlaytime = "DELETE FROM user_gametime WHERE user_id = ?";
                        try (PreparedStatement deletePlaytimeStmt = connectDB.prepareStatement(deletePlaytime)) {
                            deletePlaytimeStmt.setInt(1, userId);
                            int rowsDeleted = deletePlaytimeStmt.executeUpdate();
                        } catch (SQLException ex) {
                            System.err.println("Failed to delete from user_gametime: " + ex.getMessage());
                        }
                    }
                    resetPageContent(); //Reset page content for text box to display the updated steam ID.
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reset the text box content based on updated database changes.
     */
    private void resetPageContent() {
        int userId = SessionInfo.getUserId();
        Connection connectDB = DatabaseConnection.getInstance();
        String queryFindSteam = "SELECT user_steamId FROM user_steam WHERE user_id = ?";
        try (PreparedStatement stmt = connectDB.prepareStatement(queryFindSteam)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) { // Check if there are any rows in the result set
                    String userSteamId = rs.getString("user_steamId");
                    steamTextField.setText(userSteamId); // Steam ID found
                } else {
                    steamTextField.setText("");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Opens opt page, when OTP verifies and returns true, opens change-password page.
     *
     * @param mouseEvent Mouse click
     */
    private void changepasswordButtonOnAction(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("otp.fxml"));
            Stage otpStage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            otpStage.setScene(scene);
            OTPController controller = fxmlLoader.getController();
            otpStage.showAndWait();
            if (controller.isOtpVerified()) {
                FXMLLoader passwordLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/change-password.fxml"));
                Stage passwordStage = new Stage();
                Scene passwordScene = new Scene(passwordLoader.load(), 600, 400);
                passwordStage.setScene(passwordScene);
                passwordStage.show();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Opens opt page, when OTP verifies and returns true, delete user account and closes program.
     *
     * @param mouseEvent Mouse click
     */
    private void deleteButtonOnAction(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("otp.fxml"));
            Stage otpStage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            otpStage.setScene(scene);
            OTPController controller = fxmlLoader.getController();
            otpStage.showAndWait();
            if (controller.isOtpVerified()) {
                Connection connectDB = DatabaseConnection.getInstance();
                int userId = SessionInfo.getUserId();

                String checkSteam = "SELECT COUNT(*) FROM user_steam WHERE user_id  = ?";
                try (PreparedStatement checkSteamStmt = connectDB.prepareStatement(checkSteam)) {
                    checkSteamStmt.setInt(1, userId);
                    ResultSet steamResultSet = checkSteamStmt.executeQuery();
                    if (steamResultSet.next() && steamResultSet.getInt(1) > 0) {
                        String deleteSteam = "DELETE FROM user_steam WHERE user_id = ?";
                        try (PreparedStatement deleteSteamStmt = connectDB.prepareStatement(deleteSteam)) {
                            deleteSteamStmt.setInt(1, userId);
                            int rowsDeleted = deleteSteamStmt.executeUpdate();
                        } catch (SQLException ex) {
                            System.err.println("Failed to delete from user_steam: " + ex.getMessage());
                        }
                    }
                } catch (SQLException ex) {
                    System.err.println("Failed to check user_steam: " + ex.getMessage());
                }

                String checkPlaytime = "SELECT COUNT(*) FROM user_gametime WHERE user_id = ?";
                try (PreparedStatement checkPlaytimeStmt = connectDB.prepareStatement(checkPlaytime)) {
                    checkPlaytimeStmt.setInt(1, userId);
                    ResultSet playtimeResultSet = checkPlaytimeStmt.executeQuery();
                    if (playtimeResultSet.next() && playtimeResultSet.getInt(1) > 0) {
                        String deletePlaytime = "DELETE FROM user_gametime WHERE user_id = ?";
                        try (PreparedStatement deletePlaytimeStmt = connectDB.prepareStatement(deletePlaytime)) {
                            deletePlaytimeStmt.setInt(1, userId);
                            int rowsDeleted = deletePlaytimeStmt.executeUpdate();
                        } catch (SQLException ex) {
                            System.err.println("Failed to delete from user_gametime: " + ex.getMessage());
                        }
                    }
                } catch (SQLException ex) {
                    System.err.println("Failed to check user_gametime: " + ex.getMessage());
                }

                String checkList = "SELECT COUNT(*) FROM todolist WHERE id_account = ?";
                try (PreparedStatement checkListStmt = connectDB.prepareStatement(checkList)) {
                    checkListStmt.setInt(1, userId);
                    ResultSet listResultSet = checkListStmt.executeQuery();
                    if (listResultSet.next() && listResultSet.getInt(1) > 0) {
                        String deleteList = "DELETE FROM user_gametime WHERE id_account = ?";
                        try (PreparedStatement deleteListStmt = connectDB.prepareStatement(deleteList)) {
                            deleteListStmt.setInt(1, userId);
                            int rowsDeleted = deleteListStmt.executeUpdate();
                        } catch (SQLException ex) {
                            System.err.println("Failed to delete from todolist: " + ex.getMessage());
                        }
                    }
                } catch (SQLException ex) {
                    System.err.println("Failed to check todolist: " + ex.getMessage());
                }

                String deleteAccount = "DELETE FROM user_account WHERE account_Id = ?";
                try (PreparedStatement deleteAccountStmt = connectDB.prepareStatement(deleteAccount)) {
                    deleteAccountStmt.setInt(1, userId);
                    int rowsDeleted = deleteAccountStmt.executeUpdate();
                    Platform.exit();
                } catch (SQLException ex) {
                    System.err.println("Failed to delete from user_account: " + ex.getMessage());
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}