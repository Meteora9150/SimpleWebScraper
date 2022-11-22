package controller;

import java.io.IOException;
import java.time.YearMonth;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SiteSearcher {

	static String result="";

	public static void executeResearchbyArticle(String researchparam, String yeararticle) throws IOException {

		result="";
		
		/*researchparam="electronic research";
		researchparam="Francesco Pappalardo";*/
		
		System.out.println("researchparam = "+researchparam);
		System.out.println("yeararticle = "+yeararticle);
		
		
		Document doc = Jsoup.connect("https://dblp.uni-trier.de/search?q="+researchparam).get();
		
		if(yeararticle.isEmpty()) {
			searchWithOutYear(doc,researchparam);
		}else {
			searchWithYear(doc,researchparam,yeararticle);
		}
		
	}

	private static void searchWithYear(Document doc, String researchparam, String yeararticle) throws IOException {
		for( Element elem: doc.select("li.entry ")) {	
			if(elem.select("span[itemprop = datePublished]").text().contains(""+yeararticle)) {
				String BlobOfBIO = GetBlob(elem);

				String TextWithDoi = elem.select("span.Z3988").toString();
				String DOI="";
				
				if(TextWithDoi.indexOf("doi%2F")!=-1) {
					DOI = extractDOI(elem.select("span.Z3988").toString());
				}
				else {
					DOI="doi not found";
				}
				
				result+="relative bio of article \n"+BlobOfBIO+"\n\n";
				result+="DOI = "+DOI+"\n";
				result+="Year = "+elem.select("span[itemprop = datePublished]").text()+"\n";
				result+=elem.select("span[itemprop = author]").text()+"\n";
				result+="\n";
				result+=elem.select("span.title").text()+"\n";
				result+="------------------\n";
				
			}
		}
	}

	private static void searchWithOutYear(Document doc, String researchparam) throws IOException {
		for( Element elem: doc.select("li.entry ")) {	
			
			String TextWithDoi = elem.select("span.Z3988").toString();
			String BlobOfBIO = GetBlob(elem);
			String DOI="";
			
			if(TextWithDoi.indexOf("doi%2F")!=-1) {
				DOI = extractDOI(elem.select("span.Z3988").toString());
			}
			else {
				DOI="doi not found";
			}
			
			result+="relative bio of article \n"+BlobOfBIO+"\n\n";
			result+="DOI = "+DOI+"\n";
			result+="Year = "+elem.select("span[itemprop = datePublished]").text()+"\n";
			result+=elem.select("span[itemprop = author]").text()+"\n";
			result+="\n";
			result+=elem.select("span.title").text()+"\n";
			result+="------------------\n";
		}
	}

	private static String extractDOI(String fullSstring) {
		String TextWithDoi = fullSstring;
		int startPosition = TextWithDoi.indexOf("doi%2F");
		int endPosition = TextWithDoi.indexOf("&", startPosition);
		
		CharSequence subseq= TextWithDoi.subSequence(startPosition, endPosition);						
		String DOI = subseq.toString();
		DOI = DOI.replace("doi%2F", "");
		DOI = DOI.replaceAll("%2F", "/");
		return DOI;
	}

	private static String GetBlob(Element elem) throws IOException {
		String LinkToBibiTex = getLinkToBibiTex(elem);
		
		Document docOfBibitex = Jsoup.connect(LinkToBibiTex).get();
		String Blob = docOfBibitex.select("pre.verbatim").text().toString();
		return Blob;
	}

	private static String getLinkToBibiTex(Element elem) {
		return elem.select("nav.publ").select("li:nth-of-type(2)").select("ul").select("li:nth-of-type(1)").select("a").attr("href").toString();
	}
	
	public static String getResult() {
		return result;
	}

}
