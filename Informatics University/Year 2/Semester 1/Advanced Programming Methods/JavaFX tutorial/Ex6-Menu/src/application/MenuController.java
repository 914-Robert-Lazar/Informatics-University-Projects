package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;

public class MenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Menu fileMenu;

    @FXML
    private MenuItem newItem;

    @FXML
    private MenuItem openItem;

    @FXML
    private MenuItem exitItem;

    @FXML
    private Menu editMenu;

    @FXML
    private MenuItem copyItem;

    @FXML
    private MenuItem pasteITem;

    @FXML
    private Menu projectMenu;

    @FXML
    private CheckMenuItem buildItem;

    @FXML
    private Menu helpMenu;

    @FXML
    private RadioMenuItem updateItem1;

    @FXML
    private RadioMenuItem updateItem2;

    @FXML
    void initialize() {
    	ToggleGroup group = new ToggleGroup();
        updateItem1.setToggleGroup(group);
        updateItem2.setToggleGroup(group);
        updateItem1.setSelected(true);

    }
    
    @FXML
	public void onExit(ActionEvent event) {
    	System.out.println("TEST");
    	 System.exit(0);
    }
    }