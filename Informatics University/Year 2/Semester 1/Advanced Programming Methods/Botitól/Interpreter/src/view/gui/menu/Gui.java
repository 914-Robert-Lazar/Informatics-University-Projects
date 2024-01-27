package view.gui.menu;

import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Gui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("menu.fxml"));
        Parent root=fxmlLoader.load();
        MenuController menuController=fxmlLoader.getController();
        primaryStage.setScene(new Scene(root,500,500));
        primaryStage.setTitle("Huh");
        primaryStage.show();
        FXMLLoader fxmlLoader1=new FXMLLoader();
        fxmlLoader1.setLocation(getClass().getResource("program.fxml"));
        Parent parent=fxmlLoader1.load();
        ProgramController programController=fxmlLoader1.getController();
        menuController.setProgramController(programController);
        Stage secondaryStage=new Stage();
        secondaryStage.setTitle("Interpreter");
        secondaryStage.setScene(new Scene(parent,750,600));
        secondaryStage.show();
    }
}
