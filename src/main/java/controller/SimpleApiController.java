package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import Utilities.FileManager;
import Utilities.ProgressUpdater;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import onlineSearcher.CrosRefSearcher;
import onlineSearcher.Searcher;
import onlineSearcher.SiteSearcher;

public class SimpleApiController {

	@FXML
	private Pane SimpleApiPane;
	
	@FXML
	private ToggleGroup TypeOfSearch;
	
	@FXML
	private ToggleGroup MainOperation;
	
	@FXML
	private TextField NumberOfSearches;
    
	@FXML
	private TextField MainArgumentOfSearch;
    
	@FXML
	private TextField RequestPreview;
    
	@FXML
    private TextArea ShowResult;
	
	@FXML
	private Button Execute;
	
	String Type;
	String Operation;
	String Numbersearch;
	String MainArg;
	
	@FXML
	public void updateOperation(){
		RadioButton rb= (RadioButton) MainOperation.getSelectedToggle();
		Operation = rb.getText();
		checkRUN();
		updatequery();
	}
	
	@FXML
	public void updateSearch(){
		RadioButton rb= (RadioButton) TypeOfSearch.getSelectedToggle();
		Type = rb.getText();
		if(Type.equals("Crossref")) {
			Numbersearch="1";
			NumberOfSearches.setDisable(true);
		}else {
			NumberOfSearches.setDisable(false);
		}
		checkRUN();
		updatequery();
	}
	
	@FXML
	public void updateNumberSearch(){
		Numbersearch=NumberOfSearches.getText();
		System.out.println(Numbersearch);
		checkRUN();
		updatequery();
	}
	
	@FXML
	public void updateMainArg(){
		MainArg=MainArgumentOfSearch.getText();
		System.out.println(MainArg);
		checkRUN();
		updatequery();
	}
	
	@FXML
	public void ExecuteAPI() throws UnsupportedEncodingException, FileNotFoundException, IOException{

		String result="";
		switch(Type) {
		case "doiDB":
			SiteSearcher.executeResearchbyArticle(MainArg, "", Numbersearch);
			result=SiteSearcher.getResult();
			break;
		case "Simple":		
			Thread t2 = new Thread(new ProgressUpdater());
			t2.start();
			
			Searcher.saveFirstBatch(MainArg, null, Integer.parseInt(Numbersearch), 15);
			Searcher.saveSecondBatch(false, false, "Chrome", false);
			
			Thread t1 = new Thread(new Searcher ());
			t1.start();
			
			try {
				t1.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				t2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			result=Searcher.getResult();
			
			break;
		case "Crossref":
			CrosRefSearcher.search(MainArg);
			result=CrosRefSearcher.getText();
			break;
		}
		
		ShowResult.setText(result);
		
		if(Operation.equals("Save")) {
			System.out.println("SONO DENTRO SAVE");
			String home = System.getProperty("user.home");
			String downloadPath=home+"\\Downloads\\API_SaveFile.txt";
			FileManager.writeOnFile(downloadPath, result);
		}
	}
	
	private void checkRUN() {
		if(Type!=null && Operation!=null && Numbersearch!=null && MainArg!=null ) {
			Execute.setDisable(false);
		}else {
			Execute.setDisable(true);
		}
	}
	
	private void updatequery() {
		String query=Operation+" "+Type+" "+Numbersearch+" "+MainArg;
		RequestPreview.setText(query);
	}
	
	@FXML
	public void ReturnMainMenu(){
		final Parent root;   

    	try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/StartingMenu.fxml"));
			Scene secondLayout = new Scene(root, 950, 650);
			Stage secondaryStage = (Stage) SimpleApiPane.getScene().getWindow();	
			secondaryStage.setScene(secondLayout);
			secondaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
