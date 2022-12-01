package onlineSearcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import Utilities.FileDownloader;
import Utilities.FileManager;
import Utilities.NullValuedSearchParameters;
import Utilities.Pair;
import Utilities.ProgressUpdater;
import Utilities.TextDivider;


public class Searcher implements Runnable {
	
	static String searchTerm="";
	static List<String> additionalParams=new ArrayList<>(); 
	static int research=1;
	static int save=1;
	
	static boolean onlyPDFSearch=false;
	static boolean containsMainParam=false;
	static boolean getPDF=false;
	static String UA="Chrome";
	
	static boolean success1=false;
	static boolean success2=false; 
	
	static String Newpath;
	static String Newfilename;
	
	static boolean multipleMainParams=false;
	
	static String Browser="";
	private static String result;
	private static int max;
	
	public static void saveFirstBatch(String searchTerm, List<String> additionalParams, int research, int save) {
		//System.out.println("Entrando in Save First Batch");
		max = research;
		SetSearchTerm(searchTerm);
		SetadditionalParamsTerm(additionalParams);
		SetresearchTerm(research);
		SetsaveTerm(save);
		//System.out.println("Uscendo da Save First Batch");
	}
	
	public static void saveSecondBatch(boolean onlyPDFSearch, boolean NotInText, String UA, boolean getPDF) {
		//System.out.println("Entrando in Save Second Batch");
		SetOnlyPDFTerm(onlyPDFSearch);
		SetscontainsParamTerm(NotInText);
		SetUATerm(UA);
		SetDownloadPDF(getPDF);
		//System.out.println("Uscendo da Save Second Batch");
	}

	private static void SetDownloadPDF(boolean getPDF2) {
		getPDF=getPDF2;
	}
	private static void SetUATerm(String UA2) {
		UA=UA2;
	}
	private static void SetscontainsParamTerm(boolean NotInText) {
		containsMainParam=!NotInText;
	}
	private static void SetOnlyPDFTerm(boolean onlyPDFSearch2) {
		onlyPDFSearch=onlyPDFSearch2;
	}
	
	public static void SetSearchTerm(String searchTerm2) {
		if(searchTerm2.contains(" ")) {
			List<String> arguments= TextDivider.textSplicing(searchTerm2);
			searchTerm= TextDivider.queryAdapterOnline(arguments);
			multipleMainParams=true;
		}else {
			searchTerm=searchTerm2;
			multipleMainParams=false;
		}
		
	}
	public static void SetadditionalParamsTerm(List<String> additionalParams2) {
		additionalParams=additionalParams2;
	}
	public static void SetresearchTerm(int research2) {
		research=research2;
	}
	public static void SetsaveTerm(int save2) {
		save=save2;
	}

	public void search() throws UnsupportedEncodingException, FileNotFoundException, IOException {
		List<Pair<String,Integer>> articlelist = new ArrayList<>();
		List<Pair<String,List<String>>> articleWithKeyword = new ArrayList<>();
		List<Pair<String,String>> articleWithTitle = new ArrayList<>();
		
		if(UA.contains("Chrome")){
			System.out.println("1");
			Browser="http://www.google.com/search?q="+searchTerm+"&num="+research+2;
		}else if(UA.contains("Firefox")){
			Browser="http://www.google.com/search?q="+searchTerm+"&num="+research+2;
		}else if(UA.contains("Yahoo")){
			Browser="http://www.google.com/search?q="+searchTerm+"&num="+research+2;
		}else if(UA.contains("Google Scholar")){
			Browser="https://scholar.google.com/scholar?q="+searchTerm+"&num="+research+2;
		}

		// versione x chrome funzionante
		Document document = Jsoup
		        //.connect("http://www.google.com/search?q=" + searchTerm+ "&num="+research+2) 
				.connect(Browser) 
		        .userAgent(UA)
		        .get();
		
		int counter=1;
		int n=0;
		int valuePoints=0;
		int valuePointsInTitle=0;;
		
		// generic document scraper for test purposes saved in the project folder	
		FileManager.writeOnFile("Document Scraping .txt", document.toString());
				
		for( Element elem:document.select("a")) {
			
			String linkHref = elem.attr("href"); // i vari link https://...
			String linkText = elem.text(); // i titoli dei link https://...
	
			if(!linkHref.toLowerCase().contains("https") ||linkHref.toLowerCase().contains("amazon") ||  linkHref.toLowerCase().contains("search?q") ||  linkHref.toLowerCase().contains("youtube")) {
				// ignore article
			}
			else {
			if( (n>15) && research>0) {
				
				success1=false;
				success2=false;
				
				//System.out.println("research value before decrementation ="+research);
				research--;	
				//System.out.println("research value after decrementation ="+research);
					if(!onlyPDFSearch) {
						success1=true;
					}else {
						if(linkText.toLowerCase().contains("pdf") || linkHref.toLowerCase().contains(".pdf")) {
							success1=true;
						}
					}
					if(!multipleMainParams) {
						singleSearchParam(linkText);
					}else {
						multipleSearchParam(linkText);
					}

					boolean toCheck = success1 && success2 ;
					if(!toCheck) {
						research++;
					}
					
					if(toCheck) {			
						System.out.println("N= " + counter +" Text::   " + linkText + ",   URL::    " + linkHref);						
						System.out.println("-----------[ link N^ " + counter + " ]---------------------");	

						int value = ((counter*100)/max);
						ProgressUpdater.updateBarr(value);
						
						counter++;
			
							// replacer of strings //-----------------
							String newLinkHref= linkHref.replace("/url?q=", "");
							int start = linkHref.indexOf("&", 1);						
							int end = linkHref.length();						
							if(start!=-1) {
							CharSequence subseq= linkHref.subSequence(start, end);						
							newLinkHref = newLinkHref.replace(subseq, "");
							}						
							
					List<String> wordsFound=new ArrayList<>();		
					try {	
					Document addon = Jsoup
					        .connect(newLinkHref)
					        .userAgent(UA)
					        .get();
				
							String articleText = addon.select("p").text().toLowerCase();

							for(String par:additionalParams) {
								if(articleText.contains(par.toLowerCase())) {
									wordsFound.add(par);
									if(!NullValuedSearchParameters.contains(par)) {
										valuePoints++;
									}
								}
								if(linkText.toLowerCase().contains(par.toLowerCase())) {
									if(!NullValuedSearchParameters.contains(par)) {
										valuePointsInTitle++;
									}
								}
							}
					}
					catch(SocketException ignored){
						//ignore
						//throw new SocketException("SocketException Occurred");
					}
					catch(HttpStatusException ignored){}
					catch(Exception ignored){}
					
					// aggiornamento liste ( simil DB ) 
					valuePoints= calculatePoint(valuePoints,valuePointsInTitle);
					
					articlelist.add(new Pair<>(newLinkHref,valuePoints));
					articleWithKeyword.add(new Pair<>(newLinkHref,wordsFound));
					articleWithTitle.add(new Pair<>(newLinkHref,linkText));

					System.out.println("------[ points scored = "+valuePoints+" Words Found = "+wordsFound+" ]-------------------");
					valuePoints=0;
					valuePointsInTitle=0;
					}				
				}
			n++;
			}
		}
		
		orderArticles(articlelist);
		List<String> s;
		articlelist= selectTopN(articlelist,save);
		
		System.out.println("article list BEST = "+articlelist);
		System.out.println("-----------");
		
		s=printResults(articlelist,articleWithKeyword,articleWithTitle);
		//writeToFile(s); // crea un txt solo se il nome del file non è quello base
		
		result= FileManager.SimpleText(s);
		
		System.out.println(" --------------------------- ");
		System.out.println(" THIS IS THE END !!");
		
		if(ProgressUpdater.isStillOn()) {
			ProgressUpdater.closewindow();
		}
		
		String path=setFullPath(true);
		
		if(getPDF) {
			String home = System.getProperty("user.home");
			String downloadPath=home+"\\Downloads\\SimpleWebScraperDownloadedPDF";
			FileDownloader.download(downloadPath,articlelist,articleWithTitle,articleWithKeyword);
		}
		
		FileManager.generateHTMLPage(s,path);
	}
	
	public static String getResult() {
		return result;
	}

	private static String setFullPath(boolean extensionHTML) {
		String defaultPath="results\\";
		String defaultFile="Research result.html";
		String path;
		if(Newpath!=null) {
			defaultPath=Newpath+"\\";
		}
		if(Newfilename!=null) {
			if(extensionHTML) {
				defaultFile=Newfilename+".html";
				System.out.println("sono arrivato a html generator");
			}else {
				defaultFile=Newfilename+".txt";
				System.out.println("sono arrivato a txt generator");
			}	
		}
		path = defaultPath+""+defaultFile;
		return path;
	}
	
	private static int calculatePoint(int valuePoints, int valuePointsInTitle) {
		int result=(valuePoints*1)+(valuePointsInTitle*2);
		return result;
	}

	private static void multipleSearchParam(String linkText) {
		List<String> splice = TextDivider.textSplicing(searchTerm);
		for(String word: splice) {
			if(containsMainParam) {
				if(linkText.toLowerCase().contains(word)) {
					success2=true;
				}
			}else {
				if(!linkText.toLowerCase().contains(word)) {
					success2=true;
				}
			}
		}
		
	}

	private static void singleSearchParam(String linkText) {
		if(containsMainParam) {
			if(linkText.toLowerCase().contains(searchTerm)) {
				success2=true;
			}else {
			}
		}else {
			if(!linkText.toLowerCase().contains(searchTerm)) {
				success2=true;
			}
		}
		
	}

	private static List<Pair<String, Integer>> selectTopN(List<Pair<String, Integer>> articlelist, int topResults) {
		List<Pair<String, Integer>> best= new ArrayList<>();
		int i;
		if(topResults>articlelist.size()) {
			topResults=articlelist.size();
		}
		for(i=0;i<topResults;i++) {
			System.out.println("i= "+i+" top result ="+topResults);
			best.add(articlelist.get(i));
		}
		return best;
	}
	
	/*private static void writeToFile(List<String> s) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		String path=setFullPath(false);
		String r="";
		for(String elem:s) {
			r=r+elem+"\n";
		}
		FileManager.writeOnFile(path, r);
	}*/

	private static List<String> printResults(List<Pair<String, Integer>> articlelist, List<Pair<String, List<String>>> articleWithKeyword, List<Pair<String, String>> articleWithTitle) {
		List<String> result = new ArrayList<>();
		int Counter =1;
		for(Pair<String, Integer> al:articlelist) {
			String Link=al.getX();
			String Title;
			Integer Points=al.getY();
			List<String> Keyword;
			
			for(Pair<String, String> awt:articleWithTitle) {
				String Link2=awt.getX();
				if(Link.equals(Link2)) {
					Title=awt.getY();
					for(Pair<String, List<String>> awk:articleWithKeyword) {
						String Link3=awk.getX();
						if(Link2.equals(Link3) && !result.contains(Link)) {
							Keyword=awk.getY();
							result.add("------------[ "+Counter+" ]---------------");
							Counter++;
							result.add(" Title: "+Title);
							result.add(" Link: "+Link);
							result.add(" Points Scored: "+Points);
							result.add(" Keyword found: "+Keyword);
						}
					}
				}
			}
		}
		result.add("---------------------------------");
		return result;
	}

	private static void orderArticles(List<Pair<String, Integer>> articlelist) {
		articlelist.sort((o1,o2) -> o2.getY().compareTo(o1.getY()));;
	}

	public static void SetPath(String path2) {
		Newpath=path2;	
	}

	public static void setFilename(String filename2) {
		Newfilename=filename2;
	}

	@Override
	public void run() {
		try {
			this.search();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	
	
}
