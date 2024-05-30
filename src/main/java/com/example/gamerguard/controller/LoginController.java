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
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ResourceBundle;
import java.net.URL;


/**
 * Controller class for the login.fxml file.
 * Handles user login functionality.
 *
 * @author Serene Coders
 * @version 1.0.0
 */
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
    @FXML
    private Hyperlink signupHyperlink;


    /**
     * {@inheritDoc}
     *
     * Initializes the controller with the Gamer Guard logo image.
     * Loads the logo image from the specified file path and sets it
     * as the image source for the logoImageView.
     */
    public  void initialize (URL url, ResourceBundle resourceBundle) {
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


    /**
     * Handles the action event triggered when the signup hyperlink is clicked.
     * This method loads the sign-up FXML file and sets the scene to the new sign-up page.
     *
     * @param event the event triggered by clicking the signup hyperlink
     * @throws java.io.IOException if the FXML file cannot be loaded
     */
    public void signupHyperlinkOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) signupHyperlink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sign-up.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }


    /**
     * Handles the action event when the "Forgot Password" hyperlink is clicked.
     *
     * <p>This method loads the "otpnosession.fxml" file to display an OTP (One-Time Password)
     * input window. It initializes the new stage with a 600x400 scene and shows the OTP
     * window modally, blocking the current window until the OTP window is closed.</p>
     *
     * @param event the action event triggered by clicking the "Forgot Password" hyperlink.
     * @throws java.io.IOException if there is an error loading the FXML file.
     */
    public void forgotPasswordHyperlinkOnAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("otpnosession.fxml"));
            Stage otpStage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            otpStage.setScene(scene);
            OTPNoSessionController controller = fxmlLoader.getController();
            otpStage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Validates the user's login credentials by checking the database for a matching email and hashed password.
     * <p>
     * If a match is found, session information is updated and the dashboard is opened. If no match is found,
     * an error message is displayed. Logs any {@link SQLException} or {@link java.io.IOException} that occurs.
     * </p>
     */
    public void validateLogin() {
        Connection connectDB = DatabaseConnection.getInstance();
        String password = passwordPasswordField.getText();
        String hashedPassword = HashInput.hashInput(password);

        String verifyLogin = "SELECT count(1), account_id, firstname, emailaddress, password FROM user_account WHERE emailaddress = ?";

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin)) {
            preparedStatement.setString(1, emailTextField.getText());

            ResultSet queryResult = preparedStatement.executeQuery();

            if (queryResult.next()) {
                String storedHashedPassword = queryResult.getString("password");

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


    /**
     * Opens the dashboard scene.
     * <p>
     * This method loads the "dashboard.fxml" file to create a new scene and sets it on the current stage.
     * The scene's dimensions are defined by the constants {@code HelloApplication.WIDTH} and {@code HelloApplication.HEIGHT}.
     * The current stage is obtained from the scene of the {@code loginButton}.
     * </p>
     *
     * @throws java.io.IOException if the FXML file cannot be loaded.
     */
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
