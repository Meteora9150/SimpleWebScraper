package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Utilities.FileManager;
import Utilities.TextDivider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import onlineSearcher.Searcher;

public class MainScreenController{

	@FXML
	private Pane mainScreen;
	
	@FXML
	private Slider articlesToSearch;
	
	@FXML
	private Slider articlesToSave;
	
	@FXML
	private Label counterMaxArticles;
	
	@FXML
	private Label counterSaveArticles;
	
	@FXML
	private TextArea addParam;
	
	@FXML
	private Button addAdditonalParam;
		
	@FXML
	private TextArea showAdditionalParams;
	
	@FXML
	private TextArea mainArg;
	
	@FXML
	private TextField AddParamsNameFile;
	
	private String mainArgument="";
	private int toSave=1;
	private int toSearch=1;
	
	private List<String> additionalParams=new ArrayList<>();
	
	@FXML
	public void ToSearch() {
		int sliderValue = (int) articlesToSearch.getValue();
		articlesToSave.setMax(sliderValue);
		counterMaxArticles.setText(sliderValue+"");
		toSearch=sliderValue;
	}
	
	@FXML
	public void ToSave() {
	    int saveValue = (int) articlesToSave.getValue();
	    counterSaveArticles.setText(saveValue+"");
	    toSave=saveValue;
	}
	
	@FXML
	public void addAdditionalParams() {
		String params=addParam.getText();
		List<String> compositeParams=new ArrayList<>();
		
		if(params.contains(" ")) {
			compositeParams = TextDivider.textSplicing(params);
		}
		
		addParam.setText("");
		if( !params.isEmpty() || !compositeParams.isEmpty()) {
			if(!compositeParams.isEmpty()) {
				for(String a:compositeParams) {
					if(!additionalParams.contains(a) && a.length()!=0) {
						additionalParams.add(a);
					}
				}
			}else {
				if(!additionalParams.contains(params) && params.length()!=0) {
					additionalParams.add(params);
				}
			}
			if(additionalParams.get(additionalParams.size()-1).equals(" ")) {
				additionalParams.remove(additionalParams.size()-1);
			}
			
			refreshScreen();
		}
		
	}

	private void refreshScreen() {
		String finalText="";
		int i=1;
		for(String a:additionalParams) {
			finalText= finalText + i+" ) "+ a +"\n";
			i++;
		}
		showAdditionalParams.setText(finalText);
	}
	
	@FXML
	public void NEXT() {       

    	final Parent root;   

    	try {
			root = FXMLLoader.load(getClass().getResource("/SecondScreen.fxml"));
			Scene secondLayout = new Scene(root, 950, 650);
			Stage secondaryStage = (Stage) mainScreen.getScene().getWindow();	
			secondaryStage.setScene(secondLayout);
			secondaryStage.show();

			mainArgument=mainArg.getText();
			Searcher.saveFirstBatch(mainArgument, additionalParams, toSearch, toSave);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void SaveAdditionalParams() {
		String path = FileManager.selectDirectory();
		
		String text ="";
		for(String a:additionalParams) {
			text=text+a+"\n";
		}
		String DefaultFileName="additional params.txt";
		String FileName="";
		if(AddParamsNameFile.getText()!=null && AddParamsNameFile.getLength()>0) {
			FileName=AddParamsNameFile.getText()+".txt";
		}else {
			FileName=DefaultFileName;
		}
		FileManager.writeOnFile(path+"\\"+FileName+"", text);
	}
	
	@FXML
	public void LoadAdditionalParams() {
		List<String> params = FileManager.loadFromFile();
		params.remove(params.size()-1);
		additionalParams=params;
		refreshScreen();
	}

	@FXML
	public void ReturnMainMenu() {
		final Parent root;   

    	try {
			root = FXMLLoader.load(getClass().getResource("/StartingMenu.fxml"));
			Scene secondLayout = new Scene(root, 950, 650);
			Stage secondaryStage = (Stage) mainScreen.getScene().getWindow();
			secondaryStage.setScene(secondLayout);
			secondaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
