module com.example.gamerguard {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gamerguard to javafx.fxml;
    exports com.example.gamerguard;
}