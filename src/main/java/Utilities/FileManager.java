package Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;


public class FileManager {

	public static String selectDirectory() {
		JFileChooser f = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		
		f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
		f.showOpenDialog(null);
		File selfile = f.getSelectedFile();
		String path = selfile.getAbsolutePath();
		return path;	
	}

	public static void writeOnFile(String path, String contentToWrite) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
		        new FileOutputStream(path), "utf-8"))) {
				writer.write(contentToWrite);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> loadFromFile(){
		
		List<String> result=new ArrayList<>();
		JFileChooser f = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		
		f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); 
		f.showOpenDialog(null);
		File selfile = f.getSelectedFile();

		try {
			BufferedReader in;	
			in=new BufferedReader(new FileReader(selfile));
			String line;
			do{
	            line = in.readLine();
	            result.add(line);
	        }while (line != null);
			in.close();	
		}catch(Exception e) {
			//ignore
		}
		
		return result;
	}

	public static String SimpleText(List<String> s) throws IOException {
		String result="";
		int counter=0;
		int repetition=1;
		for(String word:s) {
			if(counter%5!=0) {
			System.out.println(counter+" ) "+word);
				switch(repetition) {
					case 1:
						result=result+word+"\n";
						break;
					case 2:
						List<String> link=TextDivider.textSplicing(word);
						result=result+link.get(link.size()-1)+"\n";
						break;
					case 3:
						result=result+word+"\n";
						break;
					case 4:
						result=result+word+"\n\n";
						repetition = 0;
						break;
				}
			repetition++;
			}
			counter++;
			
		}
		return result;
	}
	
	
	public static void generateHTMLPage(List<String> s, String path) throws IOException {
		String currentDir = System.getProperty("user.dir")+"/src/main/java/resources";
		String result="<!DOCTYPE html>\n<html>\n<head>\n<link rel=\"stylesheet\" href=\""+currentDir+"\\style.css\">\n</head>\n<body>\n"
				+ "<img src=\""+currentDir+"\\leftBracket2.png\"><h1>Research \nResults</h1><img src=\""+currentDir+"\\rightBracket2.png\">\n";
		int counter=0;
		int repetition=1;
		for(String word:s) {
			if(counter%5!=0) {
			System.out.println(counter+" ) "+word);
				switch(repetition) {
					case 1:
						result=result+"<div>";
						result=result+"<h2>"+word+"</h2>\n";
						break;
					case 2:
						List<String> link=TextDivider.textSplicing(word);
						result=result+"<a href="+link.get(link.size()-1)+">"+link.get(link.size()-1)+"</a>\n";
						break;
					case 3:
						result=result+"<p>"+word+"</p>\n";
						break;
					case 4:
						result=result+"<p>"+word+"</p>\n";
						result=result+"</div>";
						repetition = 0;
						break;
				}
			repetition++;
			}
			counter++;
		}
		result=result+"</body>\n</html>";
		writeOnFile(path, result);
	}

	public static String selectSaveFile() {
		JFileChooser f = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); 
		f.showOpenDialog(null);
		File selfile = f.getSelectedFile();
		String path = selfile.getAbsolutePath();
		return path;
	}

	
	public static void generateGenericHTMLPage(String result) {
		String currentDir = System.getProperty("user.dir")+"/src/main/java/resources";
		
		List<String> mainWords= new ArrayList<>();
		mainWords.add("Authors");
		mainWords.add("Pages");
		mainWords.add("Title");
		mainWords.add("Year");
		mainWords.add("Type");
		mainWords.add("doi");
		mainWords.add("URL");
		mainWords.add("---------");
		
		String page="<!DOCTYPE html>\n<html>\n<head>\n<link rel=\"stylesheet\" href=\""+currentDir+"\\style2.css\">\n</head>\n<body>\n";
		
		result = result.replaceAll("Authors", "<div><h2>Authors</h2><p>");
		result = result.replaceAll("Title", "</p><h3>Title</h3><p>");
		result = result.replaceAll("Pages", "</p><h3>Pages</h3><p>");
		result = result.replaceAll("Year", "</p><h3>Year</h3><p>");
		result = result.replaceAll("Type", "</p><h3>Type</h3><p>");
		result = result.replaceAll("doi", "</p><h3>doi</h3><p>");
		result = result.replaceAll("URL", "</p><h3>URL</h3><a href=");
		result = result.replaceAll("Extra", "/> link to article </a>\n<h3>Extra</h3><p>");
		result = result.replaceAll("---------", "</p></div>");
		
		page+=result;
		page+="</div>";

		String directory=FileManager.selectDirectory();
		String path=directory+"\\SimplePage.html";
		writeOnFile(path, page);
	}


}
