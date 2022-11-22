package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import onlineSearcher.GetFromSite;

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
	private TextArea ResultShowcase;
	
	@FXML
	private TextField SaveNameResults;
	
	/*@FXML
	private void RUNbyAuthor() throws IOException {
		String name = authorName.getText();
		String year = yearPublication.getText();
		
		
		//SiteSearcher.executeResearchbyAuthor(name,year);
		SiteSearcher.executeResearchbyArticle(name,year);
		String result = SiteSearcher.getResult();
		System.out.println(result);
		//ResultShowcase.setText("alfa");
		updateShowcaseResult(result);
	}*/
	
	@FXML
	private void RUNbyArticle() throws IOException {
		String researchparam = mainArgument.getText();
		String yeararticle = ArticleYear.getText();
		
		
		SiteSearcher.executeResearchbyArticle(researchparam,yeararticle);
		String result = SiteSearcher.getResult();
		System.out.println(result);
		//ResultShowcase.setText("alfa");
		updateShowcaseResult(result);
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
}
