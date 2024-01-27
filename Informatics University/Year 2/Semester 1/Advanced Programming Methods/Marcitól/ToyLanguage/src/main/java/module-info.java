module com.gui.toylanguage {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.gui.toylanguage to javafx.fxml;
    exports com.gui.toylanguage;

    opens com.gui.toylanguage.dto;
}