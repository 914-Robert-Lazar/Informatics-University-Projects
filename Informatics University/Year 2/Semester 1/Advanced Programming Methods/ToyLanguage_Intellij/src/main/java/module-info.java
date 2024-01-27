module com.example.toylanguage_intellij {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.toylanguage_intellij to javafx.fxml, javafx.base;
    opens com.example.toylanguage_intellij.Model.EntriesForGui to javafx.fxml, javafx.base;
    exports com.example.toylanguage_intellij;
}