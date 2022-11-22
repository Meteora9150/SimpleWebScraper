package onlineSearcher;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import controller.FileManager;

public class GetFromSite {

	public static void main(final String[] args) throws IOException {
		//Nithin A  // sono 270 articoli
		//Francesco magli // sono 19 articoli
		Document doc = Jsoup.connect("https://dblp.uni-trier.de/search?q=Francesco magli").get();
		//FileManager.writeOnFile("analisi di dblp.txt", doc.toString());
		//doc.select("div").attr("id").equals("completesearch-publs")
		
		//FileManager.writeOnFile("analisi di dblp pt 2.txt", doc.select("div#completesearch-publs").toString());
		//FileManager.writeOnFile("analisi di dblp pt 3.txt", doc.select("ul.publ-list").toString());
		//String year= doc.select("li.year").toString();
		
		
		for( Element elem: doc.select("li.entry ")) {
			if(elem.select("span[itemprop = datePublished]").text().contains(""+2021)) {
				
				for(Element authors:elem.select("span[itemprop = author]")) {
					if(authors.text().contains("Francesco Pappalardo")) {
						System.out.println(elem.select("span[itemprop = author]").text());
						System.out.println(elem.select("span.title").text());
						System.out.println("-----------------");
					}
				}
				
				
				//FileManager.writeOnFile("dblp analisis.txt", elem.toString());
			}
			//System.out.println("--------------");
		
			//System.out.println(elem.select("span[itemprop = datePublished]").text());
			
		}
		
		
		/*
		for( Element elem: doc.select("ul.publ-list")) {
			System.out.println(elem);
			System.out.println("--------------");
			//System.out.println(elem.select("span[itemprop = datePublished]").text());
			if(elem.select("span[itemprop = datePublished]").text().contains(""+2022)) {
				System.out.println(" ho trovato qualcosa");
				System.out.println(elem.select("title").toString());
			}
		}
		*/
		
		//System.out.println(doc);
		System.out.println("fatto!!");
		
	}
	
}
