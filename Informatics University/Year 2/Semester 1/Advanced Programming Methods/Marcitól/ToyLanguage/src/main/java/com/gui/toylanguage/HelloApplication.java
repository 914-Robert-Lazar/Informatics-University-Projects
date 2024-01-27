package com.gui.toylanguage;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader selectFxmlLoader = new FXMLLoader(getClass().getResource("select.fxml"));
        AnchorPane root = (AnchorPane) selectFxmlLoader.load();
        Scene scene = new Scene(root, 780, 550);

        stage.setTitle("Select programState!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}