module com.example.toylanguagegui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.toylanguagegui to javafx.fxml;
    exports com.example.toylanguagegui;
}