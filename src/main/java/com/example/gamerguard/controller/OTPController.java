package com.example.gamerguard.controller;

import com.example.gamerguard.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class OTPController {
    public Label otpField;
    public Button verifyButton;
    public Button cancelButton;

    public void verifyButtonOnAction(javafx.event.ActionEvent actionEvent) {
        try {
            System.out.println("Go to password changeegeg >:2");

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("change-password.fxml"));
            Stage passwordStage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            passwordStage.setScene(scene);
            ChangePasswordController controller = fxmlLoader.getController();

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
            passwordStage.show();
        } catch (IOException ex) {
            // Handle the IOException
            ex.printStackTrace();
        }
    }

    public void cancelButtonOnAction(ActionEvent actionEvent) {
    }
}
