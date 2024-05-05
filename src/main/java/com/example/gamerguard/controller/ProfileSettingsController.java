package com.example.gamerguard.controller;

import com.example.gamerguard.model.DatabaseConnection;
import com.example.gamerguard.HelloApplication;
import javafx.application.Platform;
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
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.net.URL;

public class ProfileSettingsController implements Initializable {
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
    private TextField emailTextField;
    @FXML
    private PasswordField passwordPasswordField;


    /**
     * Initializes the controller.
     * Loads the logo images from the specified file path and sets it
     * as the image source. Set event handlers for mouse click events on buttons.
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle that contains localized objects for the controller, or null if there is no resource bundle.
     */
    public  void initialize (URL url, ResourceBundle resourceBundle) {
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
    }


    /**
     * Opens the dashboard window and closes the current page.
     */
    //------------------------------------------------EIFIE FIX---------------------------------------------------------
    @FXML
    public void backButtonOnAction() throws IOException {
        Stage stage = (Stage) button_back.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }


    /**
     * Opens opt page, when OTP verifies and returns true, opens change-password page.
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
                FXMLLoader passwordLoader = new FXMLLoader(HelloApplication.class.getResource("change-password.fxml"));
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
                System.out.println(userId);
                String deleteAccount = "DELETE FROM user_account WHERE account_Id = '" + userId + "'";
                try {
                    Statement statement = connectDB.createStatement();
                    int rowsAffected = statement.executeUpdate(deleteAccount);
                    Platform.exit();
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
