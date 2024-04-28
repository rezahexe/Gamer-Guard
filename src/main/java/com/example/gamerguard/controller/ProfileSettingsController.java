package com.example.gamerguard.controller;

import com.example.gamerguard.model.DatabaseConnection;
import com.example.gamerguard.HelloApplication;
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
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;


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
     * Initializes the controller with the Gamer Guard logo image.
     * Loads the logo image from the specified file path and sets it
     * as the image source for the logoImageView.
     *
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

    @FXML
    public void backButtonOnAction() {
        System.out.println("Testing close window >:3");
        Stage stage = (Stage) button_back.getScene().getWindow();
        stage.close();
    }

    private void changepasswordButtonOnAction(MouseEvent mouseEvent) {

        try {
            System.out.println("Testing change password open new sinwpsiowiwpodi >:3");

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("otp.fxml"));
            Stage otpStage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            otpStage.setScene(scene);
            TermsAndConditionsController controller = fxmlLoader.getController();
            otpStage.show();

        } catch (IOException ex) {
            // Handle the IOException
            ex.printStackTrace();
        }

    }

    private void deleteButtonOnAction(MouseEvent mouseEvent) {
        System.out.println("Testing delete button >:3");

        Connection connectDB = DatabaseConnection.getInstance();

        int userId = SessionInfo.getUserId();
        System.out.println(userId);

        String deleteAccount = "DELETE FROM user_account WHERE account_Id = '" + userId + "'";

        try {

            Statement statement = connectDB.createStatement();
            int rowsAffected = statement.executeUpdate(deleteAccount);

            if (rowsAffected > 0) {
                System.out.println("Row deleted successfully >:3");
                // Optionally, you can perform additional actions here if a row was successfully deleted
            } else {
                System.out.println("No rows were deleted :0");
                // Optionally, you can handle the case where no rows were deleted
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

}

