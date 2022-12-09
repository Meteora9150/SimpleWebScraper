package onlineSearcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import Utilities.NullValuedSearchParameters;
import Utilities.ProgressUpdater;
import Utilities.SimplePersonalDatabase;
import Utilities.TextDivider;

public class SiteSearcher {

	static String result="";
	static int i=1;
	static int counter=1;
	static int max;
	
	
	public static void executeResearchbyArticle(String researchparam, String yeararticle, String numberOfArticles) throws IOException {
		
		Thread t2 = new Thread(new ProgressUpdater());
		t2.start();
		
		max=Integer.parseInt(numberOfArticles);
		i=1;
		result="";
		
		/*researchparam="electronic research";
		researchparam="Francesco Pappalardo";*/
		
		//System.out.println("researchparam = "+researchparam);
		//System.out.println("yeararticle = "+yeararticle);
		
		Document doc = Jsoup.connect("https://dblp.org/search/publ/api?q="+researchparam+"&h="+numberOfArticles).get();

		//System.out.println("doc is "+doc);
		//System.out.println(" hits are "+doc.select("hits").text());
		
		if(doc.select("hits").attr("total").equals("0")) {
			String message="Something went VERY WRONG I'm sorry !!\nRetry in a few moments OR try different search parameters";
			JOptionPane.showMessageDialog(null, message);
			System.out.println(message);
			ProgressUpdater.updateBarr(100);
			try {
				t2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
		
		if(yeararticle.isEmpty() || yeararticle.equals(null)) {
			searchWithOutYear(doc,researchparam);
		}else {
			searchWithYear(doc,researchparam,yeararticle);
		}
	
		//------ ora con lui ho una sorta di struttura dati ------//
		List<Map<String,String>> MappedElements = TextDivider.convertToSimilJson(result,NullValuedSearchParameters.SiteTagList);
		
		String home = System.getProperty("user.home");
		String pathToSimpleDB = home+"\\Downloads\\SimpleWebScraperDB\\DoiDB";
		SimplePersonalDatabase.InsertArticle(pathToSimpleDB,MappedElements,"DoiDB");

		//---//
		
		ProgressUpdater.updateBarr(100);	
		try {
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
	private static void searchWithYear(Document doc, String researchparam, String yeararticle) throws IOException {
		for(Element elem: doc.select("hit")) {
			if(elem.select("year").text().equals(yeararticle)) {
				result+=SetAllInfo(elem) +" \n";
				ProgressUpdater.updateBarr((counter*100)/max);
				counter++;
			}
		}
	}

	private static String SetAllInfo(Element elem) {
		String s="";
		s+="Authors ";
		for(Element authors:elem.select("authors")) {
			s+=authors.text()+" ";
		}
		s+=" \n";
		s+="Title "+elem.select("title").text()+" \n";
		s+="Pages "+elem.select("pages").text()+" \n";
		s+="Year "+elem.select("year").text()+" \n";
		s+="Type "+elem.select("type").text()+" \n";
		s+="doi "+elem.select("doi").text()+" \n";
		s+="URL "+elem.select("info > url").text()+" \n";
		s+="Extra [ write here your additions ] \n";
		s+="---------";
		i++;
		return s;
	}

	private static void searchWithOutYear(Document doc, String researchparam) throws IOException {
		for(Element elem: doc.select("hit")) {
			result+=SetAllInfo(elem) +" \n";
			ProgressUpdater.updateBarr((counter*100)/max);
			counter++;
		}
	}
	
	public static String getResult() {
		return result;
	}

}
