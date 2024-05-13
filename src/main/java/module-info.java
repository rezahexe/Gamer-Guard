module com.example.gamerguard {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;
    requires java.mail;
    requires se.michaelthelin.spotify;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires com.fasterxml.jackson.databind;


    opens com.example.gamerguard;
    opens com.example.gamerguard.controller;
    opens com.example.gamerguard.model;
    opens com.example.gamerguard.other;
    opens com.example.gamerguard.controller.Settings;
}