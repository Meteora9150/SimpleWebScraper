package controller;

import java.io.IOException;
import java.util.List;

import Utilities.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StartingMenuController {

	@FXML
	private Pane StartingScreen;
	
	@FXML
	private Pane LoadAndModifyPane;
	
	@FXML
	private TextArea ShowTXTFileContent;
	
	@FXML
	public void SimpleScraper(){
		final Parent root; 
		try {
			root = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
			Scene secondLayout = new Scene(root, 950,650);
			Stage secondaryStage = (Stage) StartingScreen.getScene().getWindow();	
			secondaryStage.setScene(secondLayout);
			secondaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void SiteScraper(){
		final Parent root;   
		
		try {
			root = FXMLLoader.load(getClass().getResource("/SiteScreen.fxml"));	
			Scene secondLayout = new Scene(root, 950,650);
			Stage secondaryStage = (Stage) StartingScreen.getScene().getWindow();	
			secondaryStage.setScene(secondLayout);
			secondaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void ToCrossref(){
		final Parent root;   
		
		try {
			root = FXMLLoader.load(getClass().getResource("/CrosrefForms.fxml"));
			Scene secondLayout = new Scene(root, 950,650);
			Stage secondaryStage = (Stage) StartingScreen.getScene().getWindow();
			secondaryStage.setScene(secondLayout);
			secondaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void ToSimpleApi(){
		final Parent root;   
		
		try {
			root = FXMLLoader.load(getClass().getResource("/SimpleApiScreen.fxml"));
			Scene secondLayout = new Scene(root, 950,650);
			Stage secondaryStage = (Stage) StartingScreen.getScene().getWindow();	
			secondaryStage.setScene(secondLayout);
			secondaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void ToDoi2Bib(){
		final Parent root;   
		
		try {
			root = FXMLLoader.load(getClass().getResource("/Doi2Bib.fxml"));
			Scene secondLayout = new Scene(root, 950,650);
			Stage secondaryStage = (Stage) StartingScreen.getScene().getWindow();	
			secondaryStage.setScene(secondLayout);
			secondaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void END(){
		System.exit(0);
	}
	
	@FXML
	public void enableMidScreen() {
		LoadAndModifyPane.setDisable(false);
		LoadAndModifyPane.setVisible(true);
		List<String> text = FileManager.loadFromFile();
		String textToShow ="";
		for(String s:text) {
			textToShow+=s+"\n";
		}
		textToShow=textToShow.substring(0, textToShow.length()-5);
		ShowTXTFileContent.setText(textToShow);
	}
	
	
	@FXML
	public void Overwrite() {
		String result = ShowTXTFileContent.getText();
		String path = FileManager.selectSaveFile();
		FileManager.writeOnFile(path, result);
	}
	
	@FXML
	public void GenerateSimpleHTML(){
		String result = ShowTXTFileContent.getText();
		FileManager.generateGenericHTMLPage(result);
	}
}
