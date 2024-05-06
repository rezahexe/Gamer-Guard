module com.example.gamerguard {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;
    requires java.mail;
    requires com.fasterxml.jackson.databind;


    opens com.example.gamerguard;
    opens com.example.gamerguard.controller;
    opens com.example.gamerguard.model;
}