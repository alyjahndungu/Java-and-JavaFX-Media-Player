package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class Main extends Application  {

	public  void start(final Stage primaryStage) {

		try {
	 Parent root =FXMLLoader.load(getClass().getResource("Xtreame.fxml"));
	Scene scene = new Scene(root);
	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
  primaryStage.initStyle(StageStyle.UNIFIED);
   primaryStage.setTitle("Qube Player");
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
