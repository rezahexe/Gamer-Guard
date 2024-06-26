package com.example.gamerguard.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


/**
 * Controller class for managing the terms and conditions view.
 *
 * @author Serene Coders
 * @version 1.0.0
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
AGREEMENT TO OUR LEGAL TERMS We are Gamer Guard('Company', 'we', 'us', or 'our'), a company registered in Australia at Brisbane. We operate the application Gamer Guard (the 'Site'), as well as any other related products and services that refer or link to these legal terms (the 'Legal Terms') (collectively, the 'Services'). You can contact us by email at or by mail to ,, Brisbane, Australia. These Legal Terms constitute a legally binding agreement made between you, whether personally or on behalf of an entity ('you'), and concerning your access to and use of the Services. You agree that by accessing the Services, you have read, understood, and agreed to be bound by all of these Legal Terms. IF YOU DO NOT AGREE WITH ALL OF THESE LEGAL TERMS, THEN YOU ARE EXPRESSLY PROHIBITED FROM USING THE SERVICES AND YOU MUST DISCONTINUE USE IMMEDIATELY. Supplemental terms and conditions or documents that may be posted on the Services from time to time are hereby expressly incorporated herein by reference. We reserve the right, in our sole discretion, to make changes or modifications to these Legal Terms from time to time. We will alert you about any changes by updating the 'Last updated' date of these Legal Terms, and you waive any right to receive specific notice of each such change. It is your responsibility to periodically review these Legal Terms to stay informed of updates. You will be subject to, and will be deemed to have been made aware of and to have accepted, the changes in any revised Legal Terms by your continued use of the Services after the date such revised Legal Terms are The Services are intended for users who are at least 13 years of age. All users who are minors in the jurisdiction in which they reside (generally under the age of 18) must have the permission of, and be directly supervised by, their parent or guardian to use the Services. If you are a minor, you must have your parent or guardian read and agree to these Legal Terms prior to you using the Services. We recommend that you print a copy of these Legal Terms for your records.""");
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
