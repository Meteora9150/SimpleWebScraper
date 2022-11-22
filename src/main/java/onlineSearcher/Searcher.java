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

import controller.FileManager;

public class Searcher {
	
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
	
	public static void saveFirstBatch(String searchTerm, List<String> additionalParams, int research, int save) {
		//System.out.println("Entrando in Save First Batch");
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
		/*if(onlyPDFSearch) {
			SetresearchTerm(research*100);
		}*/
	}

	
	
	public static void SetSearchTerm(String searchTerm2) {
		if(searchTerm2.contains(" ")) {
			List<String> arguments= TextDivider.textSplicing(searchTerm2);
			searchTerm= TextDivider.queryAdapterOnline(arguments);
			//System.out.println(" search term = "+searchTerm);
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


	public static void search() throws UnsupportedEncodingException, FileNotFoundException, IOException {
		List<Pair<String,Integer>> articlelist = new ArrayList<>();
		List<Pair<String,List<String>>> articleWithKeyword = new ArrayList<>();
		List<Pair<String,String>> articleWithTitle = new ArrayList<>();
		
		boolean showparams = false;
		if(showparams) {
			System.out.println(" search term "+searchTerm);
			System.out.println(" add term "+additionalParams);
			System.out.println(" research term "+research);
			System.out.println(" save term "+save);
			System.out.println(" onlyPDFSearch term "+onlyPDFSearch);
			System.out.println(" containsMainParam term "+containsMainParam);
			System.out.println(" UA term "+UA);
		}
		
		
		
		// versione x chrome funzionante
		Document document = Jsoup
		        .connect("http://www.google.com/search?q=" + searchTerm+ "&num="+research+2) 
		        .userAgent(UA)
		        .get();
		
		int counter=1;
		int n=0;
		int valuePoints=0;
		int valuePointsInTitle=0;
		
		//System.out.println(" blocco 1");
		
		// generic document scraper for test purposes saved in the project folder
		
		
		FileManager.writeOnFile("Document Scraping .txt", document.toString());
		
		//writeFile("Document Scraping .txt",document.toString());
		//document.select("span").attr("class").contains("MUxGbd wuQ4Ob WZ8Tjf");
		
		
		/*
		document.forEach(article->{
			
			//System.out.println("blocco AA");
			Elements qq = article.select("span");
			
			//System.out.println("blocco BB");
			
			if(qq.hasClass("r0bn4c rQMQod")) {
				//System.out.println("blocco CC");
				System.out.println("FuCK YEAH BOIIII");
			}else {
				//System.out.println("blocco DD");
				// SHIT
			}
			
			//System.out.println("blocco EE");
			System.out.println(" variable qq = "+qq);
		});
		*/
		
		for( Element elem:document.select("a")) {
			
			String linkHref = elem.attr("href"); // i vari link https://...
			String linkText = elem.text(); // i titoli dei link https://...
	
			//System.out.println(" blocco 2 N="+n);
			
			if(!linkHref.toLowerCase().contains("https") ||linkHref.toLowerCase().contains("amazon") ||  linkHref.toLowerCase().contains("search?q")) {
				//System.out.println("articolo saltato");
				//System.out.println(" blocco 3");
			}
			else {
			if( (n>15) && research>0) {
				
				success1=false;
				success2=false;
				
				//System.out.println(" blocco 4");
				System.out.println("research value before decrementation ="+research);
				research--;
				
				System.out.println("research value after decrementation ="+research);
					//System.out.println(" blocco 5");
				
					if(!onlyPDFSearch) {
						success1=true;
					}else {
						if(linkText.toLowerCase().contains("pdf") || linkHref.toLowerCase().contains(".pdf")) {
							success1=true;
							System.out.println("ho trovato un pdf per te "+success1);
						}
					}
				
					
				
					/*if(onlyPDFSearch) {
						//System.out.println(" blocco 6");
						if(linkText.toLowerCase().contains("pdf") || linkHref.toLowerCase().contains(".pdf")) {
							//System.out.println("ho trovato un pdf per te "+success1);			
							//System.out.println(" blocco 6.1");
							success1=true;
						}
						else {
							//System.out.println(" blocco 6.2 "+success1);
							//research++;
						}
					}else {
						success1=true;
						//System.out.println(" only PDf Search is set to "+onlyPDFSearch);
					}*/
				
					if(!multipleMainParams) {
						singleSearchParam(linkText);
					}else {
						multipleSearchParam(linkText);
					}
					
					/*
					if(!success2) {
						research++;
					}
					*/
					
					//boolean skip = containsMainParam || onlyPDFSearch;
					boolean toCheck = success1 && success2 ;
					if(!toCheck) {
						research++;
					}
					//System.out.println(" blocco 8 ( tocheck= "+toCheck+ " )");
					
					if(toCheck) {
						//System.out.println(" blocco 9");				
						System.out.println("N= " + counter +" Text::   " + linkText + ",   URL::    " + linkHref);						
						System.out.println("-----------[ link N^ " + counter + " ]---------------------");					
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
	
		// da decommentare 
		
		orderArticles(articlelist);
		List<String> s;
		articlelist= selectTopN(articlelist,save);
		
		
		
		s=printResults(articlelist,articleWithKeyword,articleWithTitle);
		//writeToFile(s); // crea un txt solo se il nome del file non è quello base
		
		
		
		System.out.println(" --------------------------- ");
		System.out.println(" THIS IS THE END !!");
		
		
		
		String path=setFullPath(true);
		
		if(getPDF) {
			String home = System.getProperty("user.home");
			
			String downloadPath=home+"\\Downloads\\SimpleWebScraperDownloadedPDF";
			FileDownloader.download(downloadPath,articlelist,articleWithTitle,articleWithKeyword);
		}
		
		FileManager.generateHTMLPage(s,path);
		
	}
	

	private static String setFullPath(boolean extensionHTML) {
		String defaultPath="results\\";
		String defaultFile="Research result.html";
		String path;
		if(Newpath!=null) {
			//System.out.println("newpath not null");
			defaultPath=Newpath+"\\";
			//FileManager.sendCSSstyle(defaultPath);
		}
		if(Newfilename!=null) {
			if(extensionHTML) {
				defaultFile=Newfilename+".html";
				System.out.println("sono arrivato a html generator");
			}else {
				defaultFile=Newfilename+".txt";
				System.out.println("sono arrivato a txt generator");
			}
			//System.out.println("newfilename not null");
			
		}
		
		path = defaultPath+""+defaultFile;
		return path;
	}

	private static int calculatePoint(int valuePoints, int valuePointsInTitle) {
		int result=(valuePoints*1)+(valuePointsInTitle*2);
		return result;
	}

	/*
	private static void writeFile(String path, String r) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	        new FileOutputStream(path), "utf-8"))) {
			writer.write(r);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}*/

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
				//System.out.println(linkText);
				//System.out.println(" contiene la parola");
				//System.out.println(" blocco 7 "+success2);
				success2=true;
				//research++;
			}else {
				//System.out.println(" blocco 7.5 "+success2);
				//research++;
			}
		}else {
			if(!linkText.toLowerCase().contains(searchTerm)) {
				//System.out.println(linkText);
				//System.out.println(" contiene la parola");
				//System.out.println(" blocco 7.1.0 "+success2);
				success2=true;
				//research++;
			}else {
				//System.out.println(" blocco 7.5.1 "+success2);
				//research++;
			}
			//System.out.println(" containsMainParam Search is set to "+containsMainParam);
		}
		
	}

	private static List<Pair<String, Integer>> selectTopN(List<Pair<String, Integer>> articlelist, int topResults) {
		List<Pair<String, Integer>> best= new ArrayList<>();
		int i;
		if(topResults>articlelist.size()) {
			topResults=articlelist.size();
		}
		for(i=0;i<topResults;i++) {
			best.add(articlelist.get(i));
		}
		return best;
	}
	
	private static void writeToFile(List<String> s) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		
		String path=setFullPath(false);
		/*
		String defaultPath="";
		String defaultFile="Research result.txt";
		String path;
		
		if(Newpath!=null) {
			//System.out.println("newpath not null");
			defaultPath=Newpath+"\\";
		}
		if(Newfilename!=null) {
			//System.out.println("newfilename not null");
			defaultFile=Newfilename+".txt";
		}
		path=defaultPath+""+defaultFile;
		*/
		
		
		
		
		String r="";
		for(String elem:s) {
			r=r+elem+"\n";
		}
		
		FileManager.writeOnFile(path, r);
		//writeFile(path, r);
		
	}

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
	
	
}
