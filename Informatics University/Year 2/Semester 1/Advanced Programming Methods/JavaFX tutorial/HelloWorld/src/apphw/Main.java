package apphw;
	


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			 Button btn = new Button();
		        btn.setText("Say 'Hello World'");
//		        btn.setOnAction(new EventHandler<ActionEvent>() {
//		 
//		            @Override
//		            public void handle(ActionEvent event) {
//		                System.out.println("Hello World!");
//		            }
//		        });
		        btn.setOnAction(e-> System.out.println("Hello World!"));		        
		        
			StackPane root = new StackPane();
			root.getChildren().add(btn);
			Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setTitle("Hello World!");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
