package Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimplePersonalDatabase {
	static String result="";
	
	public static void InsertArticle(String path,List<Map<String, String>> var,String type) throws IOException {
		if(type.equals("Simple")) {
			String file="Simple.txt";
			path+="\\"+file;
			String existingDatabase = getSimpleDatabase(path);
			if(existingDatabase.length()!=0) {
				existingDatabase=existingDatabase.substring(0, existingDatabase.length()-5);
			}
			String tot = FusionResults(existingDatabase,var); 
			FileManager.writeOnFile(path, tot);
		}else {
			String file="DoiDB.txt";
			path+="\\"+file;
			String existingDatabase = getSimpleDatabase(path);
			if(existingDatabase.length()!=0) {
				existingDatabase=existingDatabase.substring(0, existingDatabase.length()-5);
			}
			String tot = FusionDoiDBResults(existingDatabase,var); 
			FileManager.writeOnFile(path, tot);
		}
	}

	
	private static String FusionDoiDBResults(String existingDatabase, List<Map<String, String>> var) {
		List<Map<String, String>> existingDB;
		if(existingDatabase.isEmpty()) {
			existingDB = var;
		}else {
			existingDB = TextDivider.convertToSimilJsonforDoiDB(existingDatabase); 
		}
		List<Map<String, String>> SDB = new ArrayList<>(existingDB);	
		String fused="";
		boolean toAdd=true;
		for(Map<String, String> article:var) {
			toAdd=true;
			for(Map<String, String> exItem:existingDB) {
				if(exItem.get("Title").contains(article.get("Title"))) {
					toAdd=false;
				}
			}
			if(toAdd) {
				SDB.add(article);
			}
		}

		for(Map<String, String> article:SDB) {
			String Authors=article.get("Authors");
			String Title=article.get("Title");
			String Pages=article.get("Pages");
			String Year=article.get("Year");
			String Type=article.get("Type");
			String doi=article.get("doi");
			String URL=article.get("URL");
			fused+=" Authors "+Authors+"\n";
			fused+="Title "+Title+"\n";
			fused+="Pages "+Pages+"\n";
			fused+="Year "+Year+"\n";
			fused+="Type "+Type+"\n";
			fused+="doi "+doi+"\n";
			fused+="URL "+URL+"\n";
		}
		return fused;
	}
	
	

	private static String FusionResults(String existingDatabase, List<Map<String, String>> var) {
		List<Map<String, String>> existingDB;
		if(existingDatabase.isEmpty()) {
			existingDB = var;
		}else {
			existingDB = TextDivider.convertToSimilJsonforTextDB(existingDatabase); 
		}
		List<Map<String, String>> SDB = new ArrayList<>(existingDB);	
		String fused="";
		boolean toAdd=true;
		
		List<String> modifiedTitles= new ArrayList<>();
		List<String> modifiedKeyword= new ArrayList<>();
		
		for(Map<String, String> article:var) {
			toAdd=true;
			for(Map<String, String> exItem:existingDB) {
				if(exItem.get("Title").contains(article.get("Title"))) {
					toAdd=false;
					
					
					
					String key1=exItem.get("Keyword_found");
					String key2=article.get("Keyword_found");
					if(!key1.equals(key2)) {
						if(key1.length()!=2) {
							key1=key1.substring(1,key1.length()-1);
							key1=key1.replace("[", "");
							key1=key1.replace("]", "");
							System.out.println("key1="+key1);
						}else {
							key1="";
						}
						if(key2.length()!=2) {
							key2=key2.substring(1,key2.length());
							key2=key2.replace("[", "");
							key2=key2.replace("]", "");
							key2+=",";
							System.out.println("key2="+key2);
						}else {
							key2="";
						}
						String finalkeyword=key1+","+key2+",";
						System.out.println("final keyword = "+finalkeyword);
						String []ff= finalkeyword.split(",");
						System.out.println("ff = "+ff);
						List<String> str=new ArrayList<>();
						for(String s:ff) {
							System.out.println(" character s is ="+s);
							//System.out.println(" character length s is ="+s.length());
							if(!str.contains(s) && s.length()>=2) {
								System.out.println("adding s ="+s);
								str.add(s);
							}
						}
						System.out.println("str is finally ="+str);
						String result ="";
						for(String s:str) {
							result+=s+",";
						}
						result=result.substring(1, result.length()-1);
						System.out.println("result ="+result);
						modifiedTitles.add(article.get("Title"));
						modifiedKeyword.add(result);
					}
					
					/*
					key1= key1.replace("[", "");
					key1= key1.replace("]", "");
					
					key2= key2.replace("[", "");
					key2= key2.replace("]", "");
					String finalkeyword=key1+","+key2;
					String []ff= finalkeyword.split(",");
					List<String> str=new ArrayList<>();
					for(String s:ff) {
						if(!str.contains(s)) {
							str.add(s);
						}
					}
					String result ="";
					for(String s:str) {
						result+=s+", ";
					}
					System.out.println("result ="+result);
					
					modifiedTitles.add(article.get("Title"));
					modifiedKeyword.add(result);
					//String finalkeyword="["+key1+","+key2+"]";
					//System.out.println("final keyword = "+finalkeyword);
					*/
					
					
				}
			}
			if(toAdd) {
				SDB.add(article);
			}
		}

		for(Map<String, String> article:SDB) {
			String Title=article.get("Title");
			String Link=article.get("Link");
			String Points=article.get("Points_Scored");
			String Keyword="";
			
			
			
			if(modifiedTitles.contains(Title)) {
				int i=0;
				int end=0;
				for(i=0;i<modifiedTitles.size();i++) {
					String tt=modifiedTitles.get(i);
					if(Title.equals(tt)) {
						end=i;
					}
				}
				Keyword = "["+modifiedKeyword.get(end)+"]";
			}else {
				Keyword= article.get("Keyword_found");
			}
			
			
			
			fused+=" Title"+Title+"\n";
			fused+="Link"+Link+"\n";
			fused+="Points_Scored"+Points+"\n";
			fused+="Keyword_found"+Keyword+"\n";
		}
		return fused;
	}
	
	private static String getSimpleDatabase(String path) {
		String result="";
		File SimpleDB = new File(path);
		
		try {
			BufferedReader in;	
			in=new BufferedReader(new FileReader(SimpleDB));
			String line;
			do{
	            line = in.readLine();
	            result+=line+"\n";
	        }while (line != null);
			in.close();	
		}catch(Exception e) {
			//ignore
		}
		return result;
	}
	

}
