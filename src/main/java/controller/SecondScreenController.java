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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ProgressBar;
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
	private CheckBox downloadPDF;
	
	@FXML
	private ToggleGroup Group1;
	
	@FXML
	private Button ExecuteButton;
	
	@FXML
	private Label afterExecuteLabelForInfo;
	
	@FXML
	private Label ResearchesDone;
	
	@FXML
	private ProgressBar ProgBar;
	
	boolean NotInText=false;
	boolean OnlyPDF=false;
	boolean GetPDF=false;

	@FXML
	public void ExecuteButton() throws UnsupportedEncodingException, FileNotFoundException, IOException {
		RadioButton selectedRadioButton = (RadioButton) Group1.getSelectedToggle();
		String selectBrowser = selectedRadioButton.getText();
		if(MainParamNOTinText.isSelected()) {
			NotInText=true;
		}
		if(SearchOnlyPDF.isSelected()) {
			OnlyPDF=true;
		}
		if(downloadPDF.isSelected()) {
			GetPDF=true;
		}
		
		ExecuteButton.setDisable(true);

		String Filename=RenameReultFile.getText();
		if(!Filename.isEmpty()) {
			Searcher.setFilename(Filename);
		}	
		Searcher.saveSecondBatch(OnlyPDF, NotInText, selectBrowser, GetPDF);
		
		Thread t2 = new Thread(new ProgressUpdater());
		t2.start();
		
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
		
		returnMainScreen();
	}
	
	@FXML
	public void selectDirecory() {
        DirecorySaveFile.setText("");
		String path = FileManager.selectDirectory();
        DirecorySaveFile.setText(path);
        Searcher.SetPath(path);
	}
	
	@FXML
	public void enableDownloadPDF() {
		if(downloadPDF.isSelected()) {
			downloadPDF.setSelected(false);
		}
		downloadPDF.setDisable(!downloadPDF.isDisabled());
	}

	private void returnMainScreen() {
	
		final Parent root;   
		try {
			root = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
			Scene secondLayout = new Scene(root,950,650);
			Stage secondaryStage = (Stage) SecondScreen.getScene().getWindow();	
			secondaryStage.setScene(secondLayout);
			secondaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void setValue(double valprog) {
		ProgBar.setProgress(valprog);
	}

}
