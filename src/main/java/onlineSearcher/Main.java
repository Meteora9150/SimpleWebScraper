package onlineSearcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/resources/MainScreen.fxml"));
			//BorderPane root = new BorderPane();
			Scene scene = new Scene(root,950,650);
//			scene.getStylesheets().add(getClass().getResource().toExternalForm());
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
