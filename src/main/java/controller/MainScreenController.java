package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import onlineSearcher.TextDivider;

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
	
	

	/*
	public OnlineSearcher showOS() {
		return this.OS;
	}
	*/
	
	private int toSave=1;
	private int toSearch=1;
	
	private List<String> additionalParams=new ArrayList<>();
	
	@FXML
	public void ToSearch() {
		int sliderValue = (int) articlesToSearch.getValue();
		//System.out.println(sliderValue + " to search");
		articlesToSave.setMax(sliderValue);
		counterMaxArticles.setText(sliderValue+"");
		toSearch=sliderValue;
	}
	
	@FXML
	public void ToSave() {
	    int saveValue = (int) articlesToSave.getValue();
	    counterSaveArticles.setText(saveValue+"");
	    //System.out.println(saveValue+ " to save");
	    toSave=saveValue;
	}
	
	@FXML
	public void addAdditionalParams() {
		String params=addParam.getText();
		List<String> compositeParams=new ArrayList<>();
		
		if(params.contains(" ")) {
			
			compositeParams = TextDivider.textSplicing(params);
			/*
			CharSequence remaining="";
			do{
			//System.out.println(" cè uno spazio, rimanente = "+remaining.length());
			int spacePos = params.indexOf(" ", 0);
			//System.out.println(spacePos);
			CharSequence subseq= params.subSequence(0, spacePos);
			//System.out.println((String)subseq);
			if(!subseq.isEmpty()) {
				compositeParams.add((String)subseq);
			}
			remaining=params.subSequence(spacePos+1, params.length());
			//System.out.println((String)remaining);
			params=(String)remaining;
			System.out.println(" params remaining = "+params);
			}while(params.contains(" "));
			compositeParams.add((String)remaining);
			*/
		}
		//System.out.println(" At the end "+compositeParams);
		
		addParam.setText("");
		
		//System.out.println(" blocco1 ");
		//System.out.println(params);
		if( !params.isEmpty() || !compositeParams.isEmpty()) {
			//System.out.println(" blocco2 ");
			if(!compositeParams.isEmpty()) {
				//System.out.println(" blocco3 ");
				for(String a:compositeParams) {
					//System.out.println(" blocco4 ");
					if(!additionalParams.contains(a) && a.length()!=0) {
						additionalParams.add(a);
					}
				}
			}else {
				//System.out.println(" blocco5 ");
				if(!additionalParams.contains(params) && params.length()!=0) {
					additionalParams.add(params);
				}
			}
			//System.out.println(" blocco6 ");
			
			if(additionalParams.get(additionalParams.size()-1).equals(" ")) {
				additionalParams.remove(additionalParams.size()-1);
			}
			//System.out.println(" before refresh"+additionalParams);
			
			refreshScreen();
		}
		
	}

	private void refreshScreen() {
		//System.out.println("BLOCK 2 ) inside refresh "+additionalParams);
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
    		//System.out.println("sono dentro NEXT");
			root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/SecondScreen.fxml"));
	
			Scene secondLayout = new Scene(root, 900, 650);
			Stage secondaryStage = (Stage) mainScreen.getScene().getWindow();
				
			secondaryStage.setScene(secondLayout);
			secondaryStage.show();

			mainArgument=mainArg.getText();
			
			Searcher.saveFirstBatch(mainArgument, additionalParams, toSearch, toSave);
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
	public void SaveAdditionalParams() {
		String path = FileManager.selectDirectory();
		
		String text ="";
		for(String a:additionalParams) {
			text=text+a+"\n";
		}
		
		System.out.println(" final text to insert is "+text);
		
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
		//String LineResult = TextDivider.queryAdapterOnline(params);
		/*additionalParams=new ArrayList<>();
		for(String a:params) {
			additionalParams.add(a);
		}*/
		//System.out.println(" last elem is .. "+params.get(params.size()-1));
		params.remove(params.size()-1);
		additionalParams=params;
		//System.out.println("BLOCK 1 ) additional params is "+additionalParams);
		refreshScreen();
	}

	
}
