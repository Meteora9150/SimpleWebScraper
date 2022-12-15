package onlineSearcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

	public void start(Stage primaryStage) {
		try {
<<<<<<< HEAD
			Parent root = FXMLLoader.load(getClass().getResource("/StartingMenu.fxml"));
=======
			Parent root = FXMLLoader.load(getClass().getResource("/resources/StartingMenu.fxml"));
>>>>>>> 34e909e773934071389df648ad779b26c2b6231f
			Scene scene = new Scene(root,950,650);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch();
	}
	
	public void newScene() {
		
	}
}
