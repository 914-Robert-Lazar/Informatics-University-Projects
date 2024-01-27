package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.shape.Circle;

public class CtxMenuController {

	ContextMenu contextMenu; 

    @FXML
    private Circle circle;
    
    @FXML
    private Label label;

    @FXML
    void initialize() {
        
    	contextMenu = new ContextMenu();
    	 
        MenuItem item1 = new MenuItem("Menu Item 1");
        item1.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                label.setText("Select Menu Item 1");
            }
        });
        MenuItem item2 = new MenuItem("Menu Item 2");
        item2.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                label.setText("Select Menu Item 2");
            }
        });
 
        // Add MenuItem to ContextMenu
        contextMenu.getItems().addAll(item1, item2);
 
        // When user right-click on Circle
        circle.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
 
            @Override
            public void handle(ContextMenuEvent event) {
 
                contextMenu.show(circle, event.getScreenX(), event.getScreenY());
            }
        });
 
    	
    }
}

