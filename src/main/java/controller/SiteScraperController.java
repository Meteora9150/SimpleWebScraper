package controller;

import java.io.IOException;
import javax.swing.JOptionPane;
import Utilities.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import onlineSearcher.SiteSearcher;

public class SiteScraperController {

	@FXML
	private TextField authorName;
	
	@FXML
	private TextField yearPublication;
	
	@FXML
	private TextField mainArgument;
	
	@FXML
	private TextField ArticleYear;
	
	@FXML
	private TextField NumberOfResults;
	
	@FXML
	private TextArea ResultShowcase;
	
	@FXML
	private TextField SaveNameResults;
	
	@FXML
	private Pane SiteScreen;
	
	@FXML
	private void RUNbyArticle() throws IOException {
		String researchparam = mainArgument.getText();
		String yeararticle = ArticleYear.getText();
		String numberOfArticles= NumberOfResults.getText();
		
		
		if(numberOfArticles.isEmpty()) {
			numberOfArticles="20";
		}
		if(!researchparam.isEmpty()) {
			SiteSearcher.executeResearchbyArticle(researchparam,yeararticle,numberOfArticles);
			String result = SiteSearcher.getResult();
					//System.out.println(result);
			updateShowcaseResult(result);
		}else {
			// insert block alert 
			String infoMessage = "Please enter a valid research param !!";
			//String titleBar = "Stop ! you violated the law";
			String titleBar = "Error !!";
			JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE); 
		}
	}
	
	@FXML
	private void SaveScreen() {
		String extension=".txt";
		String defaultName="Results"+extension;
		String SaveNameFile="";
		String Directory=FileManager.selectDirectory();
		System.out.println("directory= "+Directory);
		
		if(SaveNameResults.getText().isEmpty()) {
			SaveNameFile=defaultName;
		}else {
			SaveNameFile=SaveNameResults.getText()+extension;
		}
		
		String path = Directory+"\\"+SaveNameFile;
		System.out.println("path= "+path);
		FileManager.writeOnFile(path,ResultShowcase.getText());
	}
	
	private void updateShowcaseResult(String result) {
		ResultShowcase.setWrapText(true);
		ResultShowcase.setText(result);
	}
	
	@FXML
	private void ReturnToMenu(){
		final Parent root;   

    	try {
			root = FXMLLoader.load(getClass().getResource("/StartingMenu.fxml"));
			Scene secondLayout = new Scene(root, 950, 650);
			Stage secondaryStage = (Stage) SiteScreen.getScene().getWindow();	
			secondaryStage.setScene(secondLayout);
			secondaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
