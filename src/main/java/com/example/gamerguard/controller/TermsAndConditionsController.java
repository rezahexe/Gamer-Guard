package com.example.gamerguard.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


/**
 * Controller class for managing the terms and conditions view.
 */
public class TermsAndConditionsController {

    @FXML
    private TextArea termsAndConditions;
    @FXML
    private CheckBox agreeCheckBox;
    @FXML
    private Button nextButton;
    @FXML
    private Button registerButton;


    /**
     * Initializes the controller.
     * Sets the text of the terms and conditions TextArea.
     */
    @FXML
    public void initialize(){
        termsAndConditions.setText(""" 
Lorem ipsum dolor sit amet, consectetur adipiscing elit,
sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
Eget dolor morbi non arcu risus. Quis lectus nulla at volutpat diam
ut venenatis tellus in. Feugiat in fermentum posuere urna nec tincidunt
praesent semper. Turpis tincidunt id aliquet risus feugiat in.
Libero volutpat sed cras ornare. Facilisi morbi tempus iaculis urna.
Bibendum est ultricies integer quis auctor. Eu augue ut lectus arcu.
Tincidunt tortor aliquam nulla facilisi cras fermentum odio eu.
Gravida neque convallis a cras. Elit ut aliquam purus sit.
Suspendisse ultrices gravida dictum fusce ut placerat.
Integer feugiat scelerisque varius morbi enim nunc.
Amet justo donec enim diam vulputate ut pharetra.
Sapien pellentesque habitant morbi tristique.
Lorem sed risus ultricies tristique nulla aliquet.
Elementum nibh tellus molestie nunc non blandit massa.""");
    }


    /**
     * Handles the action when the agree CheckBox is clicked.
     * Disables or enables the nextButton based on whether the CheckBox is selected.
     */
    @FXML
    protected void  onAgreeCheckBoxClick() {
        boolean accepted = agreeCheckBox.isSelected();
        nextButton.setDisable(!accepted);
    }


    /**
     * Sets the registerButton.
     *
     * @param registerButton the registerButton to set
     */
    public void setRegisterButton(Button registerButton) {
        this.registerButton = registerButton;
    }


    /**
     * Handles the action when the nextButton is clicked.
     * Enables the registerButton and closes the current stage.
     */
    @FXML
    protected void onNextButtonClick() {
        Stage stage = (Stage) nextButton.getScene().getWindow();
        registerButton.setDisable(false);
        stage.close();
    }


    /**
     * Handles the action when the cancelButton is clicked.
     * Closes the current stage.
     */
    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) nextButton.getScene().getWindow();
        stage.close();
    }


}
