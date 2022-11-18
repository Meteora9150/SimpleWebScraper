package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import onlineSearcher.Searcher;


public class SecondScreenController {
	
	@FXML
	private TextField RenameReultFile;
	
	@FXML
	private TextField DirecorySaveFile;
	
	@FXML
	private Pane SecondScreen;
	
	@FXML
	private CheckBox MainParamNOTinText;
	
	@FXML
	private CheckBox SearchOnlyPDF;		
	
	@FXML
	private ToggleGroup Group1;
	
	@FXML
	private Button ExecuteButton;
	
	boolean NotInText=false;
	boolean OnlyPDF=false;
	
	//OnlineSearcher OS= new MainScreenController().showOS();
	
	
	@FXML
	public void ExecuteButton() throws UnsupportedEncodingException, FileNotFoundException, IOException {
		RadioButton selectedRadioButton = (RadioButton) Group1.getSelectedToggle();
		String selectBrowser = selectedRadioButton.getText();
		//System.out.println("browser selected is ... "+selectBrowser);
		if(MainParamNOTinText.isSelected()) {
			NotInText=true;
		}
		if(SearchOnlyPDF.isSelected()) {
			OnlyPDF=true;
		}
		
		
		//String browser="Chrome";
		/*
		switch(selectBrowser) {
		case"Chrome":
			browser="Chrome";
			break;
		case"FireFox":
			browser="Firefox";
			break;
		case"Google Scholar":
			browser="scholar.google.com";
			break;
		case"Yahoo":
			browser="Yahoo";
			break;
		}
		*/
		ExecuteButton.setDisable(true);
		
		String Filename=RenameReultFile.getText();
		//System.out.println("filename in second controller is"+Filename+" leng : "+Filename.length()+" is empty ? "+Filename.isEmpty());
		if(!Filename.isEmpty()) {
			//System.out.println("filename CHECK in second controller ");
			Searcher.setFilename(Filename);
		}
		
		//selectDirecory();
		
		Searcher.saveSecondBatch(OnlyPDF, NotInText, selectBrowser);
		Searcher.search();
		
		final Parent root;   
		try {
    		//System.out.println("sono dentro NEXT");
			root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/MainScreen.fxml"));
	
			Scene secondLayout = new Scene(root,950,650);
			Stage secondaryStage = (Stage) SecondScreen.getScene().getWindow();
				
			secondaryStage.setScene(secondLayout);
			secondaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		OS.SetUA(browser);
		OS.SetSearchPDF(OnlyPDF);
		OS.SetNotInText(NotInText);
		OS.ExecuteResearch();
		*/
		//System.exit(0);

	}
	
	@FXML
	public void selectDirecory() {
        DirecorySaveFile.setText("");
		String path = FileManager.selectDirectory();

        DirecorySaveFile.setText(path);
        
        
        Searcher.SetPath(path);
	}

}
