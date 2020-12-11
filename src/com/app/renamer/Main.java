package com.app.renamer;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * 
 * @author fytta
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("MainView.fxml"));
			Scene scene = new Scene(root,600,550);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Multifile renamer");
			primaryStage.show();
			primaryStage.setResizable(false);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
