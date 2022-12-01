package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import onlineSearcher.CrosRefSearcher;

public class CrosrefFormsController {
	
	@FXML
	private Pane CrosrefFormsPane;
	
	@FXML
	private TextField DoiInsertion;
	
	@FXML
	private TextArea resultScreen;
	
	@FXML
	public void BackToMenu(){
		final Parent root;   

    	try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/StartingMenu.fxml"));
			Scene secondLayout = new Scene(root, 950, 650);
			Stage secondaryStage = (Stage) CrosrefFormsPane.getScene().getWindow();
				
			secondaryStage.setScene(secondLayout);
			secondaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void Execute() throws IOException{
		String doi= DoiInsertion.getText();
		CrosRefSearcher.search(doi);
		updateScreen();
	}

	private void updateScreen() {
		String text = CrosRefSearcher.getText();
		resultScreen.setText(text);
		
	}
	
}
