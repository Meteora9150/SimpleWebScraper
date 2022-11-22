package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StartingMenuController {

	@FXML
	private Pane StartingScreen;
	
	@FXML
	public void SimpleScraper(){
		final Parent root; 
		try {
    		//System.out.println("sono dentro NEXT");
			root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/MainScreen.fxml"));
			//
			Scene secondLayout = new Scene(root, 950,650);
			Stage secondaryStage = (Stage) StartingScreen.getScene().getWindow();
				
			secondaryStage.setScene(secondLayout);
			secondaryStage.show();

			/*
			OS.SetMainArg(mainArgument);
			OS.SetAdditionalTextParams(additionalParams);
			OS.SetToSearch(toSearch);
			OS.SetToSave(toSave);
			*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void SiteScraper(){
		final Parent root;   
		
		try {
    		//System.out.println("sono dentro NEXT");
			root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/SiteScreen.fxml"));
	
			Scene secondLayout = new Scene(root, 950,650);
			Stage secondaryStage = (Stage) StartingScreen.getScene().getWindow();
				
			secondaryStage.setScene(secondLayout);
			secondaryStage.show();

			/*
			OS.SetMainArg(mainArgument);
			OS.SetAdditionalTextParams(additionalParams);
			OS.SetToSearch(toSearch);
			OS.SetToSave(toSave);
			*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void END(){
		System.exit(0);
	}
	
}
