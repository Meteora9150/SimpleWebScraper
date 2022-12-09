package Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TextDivider {

	public static List<String> textSplicing(String params) {
		List<String> compositeParams=new ArrayList<>();
		
		CharSequence remaining="";
		do{
		int spacePos = params.indexOf(" ", 0);
		CharSequence subseq= params.subSequence(0, spacePos);
		String sub= (String) subseq;
		if(!sub.isEmpty()) {
			compositeParams.add((String)subseq);
		}
		remaining=params.subSequence(spacePos+1, params.length());
		params=(String)remaining;
		}while(params.contains(" "));
		compositeParams.add((String)remaining);
		return compositeParams;
	}
	
	public static String queryAdapterOnline(List<String> arguments) {
		String result="";
		for(String s:arguments) {
			if(result=="") {
				result=result+s;
			}else {
				result=result+" "+s;
			}

		}
		return result;
	}
	
	
		public static List<Map<String, String>> convertToSimilJson(String data, String[] tagListToSearch) {
			List<Map<String,String>> MappedElements = new ArrayList<>();
			String[] searchTagList = tagListToSearch;
			Map<String,String> MapOfParameters= new HashMap<>();
			
			//System.out.println(data);
			
			Scanner scanner = new Scanner(data);
			while (scanner.hasNextLine()) {
			  
			  String Tag;
			  String content;
			  String line = scanner.nextLine();
			  
			  //System.out.println("new line )"+line +"\n");
			  for(String elem:searchTagList) {
				  if(line.contains(elem)) {
					 // counter++;
					 // System.out.println("trovato"+elem +"\n");
					  Tag=elem;
					  CharSequence toRemove= Tag;
					  line = line.replace(toRemove,"");
					  content=line;
					 // System.out.println("Tag = "+Tag+" Content = "+content +"\n");
					  MapOfParameters.put(Tag, content);
					  //System.out.println(MapOfParameters);
				  }
			  }
			  if(line.contains("--------- ") || line.contains("------------")) {
				  MappedElements.add(MapOfParameters);
				  MapOfParameters = new HashMap<>();
				  //System.out.println("nuovo elemento" +"\n");
			  }
			}
			scanner.close();
			System.out.println(" mapped elements is "+MappedElements);
			return MappedElements;
	}
		
		public static List<Map<String, String>> convertToSimilJsonforTextDB(String existingDatabase) {
			String[] simpletaglist=NullValuedSearchParameters.SimpleTagList;
			List<Map<String,String>> MappedElements = new ArrayList<>();
			System.out.println(existingDatabase);
			Map<String,String> MapOfParameters= new HashMap<>();
			
			//System.out.println(data);
			System.out.println("---------- entro dentro il converti simil text to json per simple DB ------------");
			
			int counter=0;
			Scanner scanner = new Scanner(existingDatabase);
			while (scanner.hasNextLine()) {
			  
			  String Tag;
			  String content;
			  String line = scanner.nextLine();
			  
			  //System.out.println("new line ) // "+line);
			  for(String elem:simpletaglist) {
				  if(line.contains(elem) &&counter<4) {
					  //System.out.println("counter is ="+counter);
					  //System.out.println("-------trovato"+elem+"-------");
					  Tag=elem;
					  CharSequence toRemove= Tag;
					  line = line.replace(toRemove,"");
					  if(counter==0) {
						  line=line.substring(1, line.length());
					  }
					  content=line;
					  //System.out.println("Tag = "+Tag+" Content = "+content);
					  MapOfParameters.put(Tag,content);
					  counter++;
					  //System.out.println(MapOfParameters);
				  }
			  }
			  if(counter>=4) {
				  //System.out.println("------- INSERIMENTO NUOVO ELEMENTO -------");
				  //System.out.println("MAP ="+MapOfParameters);
				  MappedElements.add(MapOfParameters);
				  MapOfParameters = new HashMap<>();
				  counter=0;
			  }
			}
			scanner.close();
			System.out.println(" mapped elements is "+MappedElements);
			return MappedElements;
		}

		public static List<Map<String, String>> convertToSimilJsonforDoiDB(String existingDatabase) {
			String[] simpletaglist=NullValuedSearchParameters.SiteTagList;
			List<Map<String,String>> MappedElements = new ArrayList<>();
			System.out.println(existingDatabase);
			Map<String,String> MapOfParameters= new HashMap<>();
			
			//System.out.println(data);
			System.out.println("---------- entro dentro il converti simil text to json per Doi DB ------------");
			
			int counter=0;
			Scanner scanner = new Scanner(existingDatabase);
			while (scanner.hasNextLine()) {
			  
			  String Tag;
			  String content;
			  String line = scanner.nextLine();
			  
			  //System.out.println("new line ) // "+line);
			  for(String elem:simpletaglist) {
				  if(line.contains(elem) &&counter<7) {
					  //System.out.println("counter is ="+counter);
					  //System.out.println("-------trovato"+elem+"-------");
					  Tag=elem;
					  CharSequence toRemove= Tag+" ";
					  line = line.replace(toRemove,"");
					  if(counter==0) {
						  line=line.substring(1, line.length());
					  }
					  content=line;
					  //System.out.println("Tag = "+Tag+" Content = "+content);
					  MapOfParameters.put(Tag, content);
					  counter++;
				  }
			  }
			  if(counter>=7) {
				  //System.out.println("------- INSERIMENTO NUOVO ELEMENTO -------");
				  //System.out.println("MAP ="+MapOfParameters);
				  MappedElements.add(MapOfParameters);
				  MapOfParameters = new HashMap<>();
				  counter=0;
			  }
			}
			scanner.close();
			System.out.println(" mapped elements is "+MappedElements);
			return MappedElements;
		}
	
}
