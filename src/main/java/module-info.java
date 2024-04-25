module com.example.gamerguard {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;


    opens com.example.gamerguard;
    opens com.example.gamerguard.controller;
    opens com.example.gamerguard.model;
//            to javafx.fxml;
//    exports com.example.gamerguard;
}