package controller;

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

import onlineSearcher.TextDivider;

public class FileManager {

	public static String selectDirectory() {
		JFileChooser f = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		
		f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
		f.showOpenDialog(null);
		File selfile = f.getSelectedFile();
		String path = selfile.getAbsolutePath();
		//System.out.println(path);
		return path;	
	}

	public static void writeOnFile(String path, String contentToWrite) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
		        new FileOutputStream(path), "utf-8"))) {
				writer.write(contentToWrite);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<String> loadFromFile(){
		
		List<String> result=new ArrayList<>();
		JFileChooser f = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		
		f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); 
		f.showOpenDialog(null);
		File selfile = f.getSelectedFile();
		//System.out.println("file = "+selfile); // continua questa parte ( PS manca il ; ) 
		
		try {
			BufferedReader in;
			
			in=new BufferedReader(new FileReader(selfile));
			//String line= in.readLine();
			//System.out.println("line is ="+line);
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

	public static void generateHTMLPage(List<String> s, String path) throws IOException {
		//System.out.println(" current dir = "+System.getProperty("user.dir"));
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
						//System.out.println("Repetition is 1 but actually is ="+repetition);
						result=result+"<div>";
						result=result+"<h2>"+word+"</h2>\n";
						break;
					case 2:
						//System.out.println("Repetition is 2 but actually is ="+repetition);
						List<String> link=TextDivider.textSplicing(word);
						/*System.out.println(link);
						System.out.println(" 0) "+link.get(0));
						System.out.println(" 1) "+link.get(1));*/
						result=result+"<a href="+link.get(link.size()-1)+">"+link.get(link.size()-1)+"</a>\n";
						break;
					case 3:
						//System.out.println("Repetition is 3 but actually is ="+repetition);
						result=result+"<p>"+word+"</p>\n";
						break;
					case 4:
						//System.out.println("Repetition is 4 but actually is ="+repetition);
						result=result+"<p>"+word+"</p>\n";
						result=result+"</div>";
						repetition = 0;
						break;
					/*case 5:
						//System.out.println("Repetition is 5 but actually is ="+repetition);
						repetition=0;
						break;*/
				}
			repetition++;
			}
			counter++;
			
		}
		result=result+"</body>\n</html>";
		writeOnFile(path, result);
	}



}
