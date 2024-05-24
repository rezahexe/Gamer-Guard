package com.example.gamerguard.controller;

import com.example.gamerguard.model.DatabaseConnection;
import com.example.gamerguard.HelloApplication;
import com.example.gamerguard.other.HashInput;
import com.example.gamerguard.other.SessionInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ResourceBundle;
import java.net.URL;

public class LoginController implements Initializable {

    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView logoImageView;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordPasswordField;
/*    @FXML
    private Hyperlink forgotPasswordHyperlink;*/
    @FXML
    private Hyperlink signupHyperlink;


    /**
     * Initializes the controller with the Gamer Guard logo image.
     * Loads the logo image from the specified file path and sets it
     * as the image source for the logoImageView.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle that contains localized objects for the controller, or null if there is no resource bundle.
     */
    public  void initialize (URL url, ResourceBundle resourceBundle) {
        String userInput = "1";
        String hashed = HashInput.hashInput(userInput);
        System.out.println("Hashed input: " + hashed);

        File logoFile = new File("Images/GAMER_GUARD_LOGO.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);
    }


    /**
     * Handles the action event when the login button is clicked.
     * Validates whether the email and password fields are filled.
     * If both fields are filled, calls the validateLogin() method.
     * If any field is empty, updates the loginMessageLabel to prompt
     * the user to enter both email and password.
     *
     * @param event The ActionEvent triggered by clicking the login button.
     */
    public void loginButtonOnAction(ActionEvent event) {
        if (emailTextField.getText().isBlank() == false && passwordPasswordField.getText().isBlank() == false)  {
            validateLogin();

        } else {
            loginMessageLabel.setText("Please enter a username and password");

        }
    }

    public void signupHyperlinkOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) signupHyperlink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sign-up.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    public void forgotPasswordHyperlinkOnAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("otpnosession.fxml"));
            Stage otpStage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            otpStage.setScene(scene);
            OTPController controller = fxmlLoader.getController();
            otpStage.showAndWait();
            if (controller.isOtpVerified()) {
                FXMLLoader passwordLoader = new FXMLLoader(HelloApplication.class.getResource("forgotpassword.fxml"));
                Stage passwordStage = new Stage();
                Scene passwordScene = new Scene(passwordLoader.load(), 600, 400);
                passwordStage.setScene(passwordScene);
                passwordStage.show();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

//    public void forgotPasswordHyperlinkOnAction(ActionEvent event) throws IOException {
////        Stage stage = (Stage) forgotPasswordHyperlink.getScene().getWindow();
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forgotpassword.fxml"));
//        Stage forgotPasswordStage = new Stage();
//        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
//        forgotPasswordStage.setScene(scene);
//        forgotPasswordStage.show();
//    }



    public void validateLogin() {
        Connection connectDB = DatabaseConnection.getInstance();
        String password = passwordPasswordField.getText();
        String hashedPassword = HashInput.hashInput(password);
        System.out.println("Hashed input: " + hashedPassword);

        // Include the password column in the query for debugging
        String verifyLogin = "SELECT count(1), account_id, firstname, emailaddress, password FROM user_account WHERE emailaddress = ?";

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin)) {
            preparedStatement.setString(1, emailTextField.getText());

            ResultSet queryResult = preparedStatement.executeQuery();

            if (queryResult.next()) {
                // Print the stored hashed password for debugging
                String storedHashedPassword = queryResult.getString("password");
                System.out.println("Stored hashed password: " + storedHashedPassword);

                if (queryResult.getInt(1) == 1 && storedHashedPassword.equals(hashedPassword)) {
                    int userId = queryResult.getInt("account_id");
                    SessionInfo.setUserId(userId);
                    String userName = queryResult.getString("firstname");
                    SessionInfo.setUserName(userName);
                    String userEmail = queryResult.getString("emailaddress");
                    SessionInfo.setUserEmail(userEmail);
                    openDashboard();
                } else {
                    loginMessageLabel.setText("Invalid login. Please try again");
                }
            } else {
                loginMessageLabel.setText("Invalid login. Please try again");
            }

        } catch (SQLException | IOException ex) {
            System.err.println(ex);
        }
    }



    public void openDashboard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(scene);
    }


    /**
     * Handles the action event when the cancel button is clicked.
     * Retrieves the stage associated with the cancel button's scene
     * and closes it, effectively canceling the current operation.
     *
     * @param event The ActionEvent triggered by clicking the cancel button.
     */
    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}