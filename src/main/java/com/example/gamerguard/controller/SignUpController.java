package com.example.gamerguard.controller;

import com.example.gamerguard.model.DatabaseConnection;
import com.example.gamerguard.HelloApplication;
import com.example.gamerguard.other.HashInput;
import com.example.gamerguard.other.SessionInfo;
import javafx.application.Platform;
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
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class SignUpController implements Initializable {

    @FXML
    private ImageView logoImageView;
    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label registerMessageLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Hyperlink termsAndConditionsHyperlink;
    @FXML
    private Hyperlink loginHyperlink;

    public static Button registerButtonStatic;


    /**
     * Initializes the controller with the Gamer Guard logo image.
     * Loads the logo image from the specified file path and sets it
     * as the image source for the logoImageView.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle that contains localized objects for the controller, or null if there is no resource bundle.
     */
    public  void initialize (URL url, ResourceBundle resourceBundle) {
        registerButtonStatic = registerButton;
        File logoFile = new File("Images/GAMER_GUARD_LOGO.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);
    }


    /**
     * Registers a new user by retrieving input, hashing the password, checking for email uniqueness, and inserting the user data into the database.
     *
     * @return {@code true} if registration is successful, {@code false} otherwise.
     *
     * The method performs the following steps:
     * <ol>
     *   <li>Retrieves user input from text fields and hashes the password.</li>
     *   <li>Checks if the email address is already registered.</li>
     *   <li>If the email is registered, displays an error message and returns {@code false}.</li>
     *   <li>If not, inserts the user data into the database and sets session information.</li>
     * </ol>
     *
     * <p>Exceptions during email check or data insertion are caught, logged, and result in {@code false} being returned.</p>
     *
     * <p><b>Note:</b> Assumes {@code DatabaseConnection}, {@code HashInput}, {@code SessionInfo}, and relevant UI components are defined and accessible.</p>
     */
    public boolean registerUser() {
        Connection connectDB = DatabaseConnection.getInstance();

        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String emailaddress = emailTextField.getText();
        String password = setPasswordField.getText();
        String hashedpassword = HashInput.hashInput(password);

        String checkEmailQuery = "SELECT COUNT(*) FROM user_account WHERE emailaddress = ?";
        try (PreparedStatement checkEmailStatement = connectDB.prepareStatement(checkEmailQuery)) {
            checkEmailStatement.setString(1, emailaddress);
            ResultSet resultSet = checkEmailStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                registerMessageLabel.setText("Email address is already registered, try resetting password.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        String insertFields = "INSERT INTO user_account(firstname, lastname, emailaddress, password) VALUES ('";
        String insertValues = firstname + "','"+ lastname + "','" + emailaddress + "','" + hashedpassword + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

            registerMessageLabel.setText("Registered successfully!");

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    SessionInfo.setUserId(userId);
                }
            }
            SessionInfo.setUserName(firstname);
            SessionInfo.setUserEmail(emailaddress);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            return false;
        }
    }


    /**
     * Handles the action event triggered when the terms and conditions hyperlink is clicked.
     * <p>
     * This method loads the "termsandconditions.fxml" file to display the terms and conditions
     * in a new window. The new window is set with a fixed size of 600x400 pixels. The controller
     * for the terms and conditions view is obtained from the FXMLLoader, and the register button
     * is passed to the controller for further interactions.
     * </p>
     *
     * @param event The action event triggered by clicking the terms and conditions hyperlink.
     * @throws IOException If an input or output exception occurs while loading the FXML file.
     */
    public void termsAndConditionsHyperlinkOnAction(ActionEvent event) throws IOException {
//        Stage stage = (Stage) termsAndConditionsHyperlink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("termsandconditions.fxml"));
        Stage termsAndConditionsStage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        termsAndConditionsStage.setScene(scene);

        TermsAndConditionsController controller = fxmlLoader.getController();
        controller.setRegisterButton(registerButton);
        termsAndConditionsStage.show();
    }


    /**
     * Handles the action event triggered by the register button.
     * <p>
     * This method retrieves the input from text fields for first name, last name, email address, password,
     * and confirm password. It validates that none of these fields are empty and that the password matches
     * the confirmed password. If all validations pass, it attempts to register the user by calling
     * {@code registerUser()}. If the registration is successful, it opens the dashboard by calling
     * {@code openDashboard()}. If an IOException occurs during this process, it prints the stack trace.
     * </p>
     * <p>
     * In case of validation failure, appropriate error messages are displayed on {@code registerMessageLabel}.
     * </p>
     *
     * @param event the action event triggered by the register button
     */
    public void registerButtonOnAction (ActionEvent event) {

        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String emailaddress = emailTextField.getText();
        String password = setPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!firstname.isEmpty() && !lastname.isEmpty() && !emailaddress.isEmpty() && !password.isEmpty()) {
            if (password.equals(confirmPassword)) {
                if (registerUser()) {
                    try {
                        openDashboard();
                    } catch (IOException e) {
                        e.printStackTrace();
                        e.getCause();
                    }
                }
            } else {
                registerMessageLabel.setText("Password does not match!");
            }
        } else {
            registerMessageLabel.setText("All fields must be filled");
        }

    }


    /**
     * Handles the action event when the login hyperlink is clicked.
     * Loads the login form from an FXML file and sets it as the scene for the current stage.
     *
     * @param event The ActionEvent triggered by clicking the login hyperlink.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public void loginHyperlinkOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) loginHyperlink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }


    /**
     * Opens the dashboard scene.
     * <p>
     * This method loads the "dashboard.fxml" file to create a new scene and sets it on the current stage.
     * The scene's dimensions are defined by the constants {@code HelloApplication.WIDTH} and {@code HelloApplication.HEIGHT}.
     * The current stage is obtained from the scene of the {@code registerButton}.
     * </p>
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    public void openDashboard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.setScene(scene);
    }


    /**
     * Handles the action event triggered by the cancel button.
     * Closes the current stage and exits the application platform.
     *
     * @param event The ActionEvent triggered by the cancel button.
     */
    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

}

