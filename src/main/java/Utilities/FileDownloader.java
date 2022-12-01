package Utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileDownloader {

	public static void download(String path, List<Pair<String, Integer>> articlelist, List<Pair<String, String>> articleWithTitle, List<Pair<String, List<String>>> articleWithKeyword) throws IOException {
		File theDir = new File(path);
		if (!theDir.exists()){
		    theDir.mkdirs();
		}
		
		List<Pair<String,String>> onlyPDF = extractPDF( articlelist, articleWithTitle,articleWithKeyword);
		int n=1;
		//System.out.println("oh funziona?");
		for( Pair<String, String> pdf:onlyPDF) {
			
			System.out.println("trovato link "+pdf.getX());
		
			int maxlength= pdf.getY().length();
			int allowedLength=0;
			if(maxlength<=40) {
				allowedLength=maxlength;
			}else {
				allowedLength=40;
			}
			
			String title=pdf.getY().substring(0, allowedLength);;
			
			try {
			InputStream fileIn;
			FileOutputStream fileOut;
			URL url= new URL(pdf.getX());
			
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			fileIn = conn.getInputStream();
			fileOut = new FileOutputStream(path+"\\"+title+".pdf");//// or you can hard code the filename
	        n++;
			// Read data into buffer and then write to the output file
	        byte[] buffer = new byte[8192];
	        int bytesRead;
		        while ((bytesRead = fileIn.read(buffer)) != -1) {
		        	fileOut.write(buffer, 0, bytesRead);
		        }
		    fileIn.close();
		    fileOut.close();
			}
			catch(Exception e){
				//ignore
			}
		}
	}

	private static List<Pair<String, String>> extractPDF(List<Pair<String, Integer>> articlelist, List<Pair<String, String>> articleWithTitle, List<Pair<String, List<String>>> articleWithKeyword ) {
		
		List<Pair<String,String>> foundPDF = new ArrayList<>();
		for(Pair<String, Integer> al:articlelist) {
			String Link=al.getX();
			String Title;
			for(Pair<String, String> awt:articleWithTitle) {
				String Link2=awt.getX();
				if(Link.equals(Link2)) {
					Title=awt.getY();
					for(Pair<String, List<String>> awk:articleWithKeyword) {
						String Link3=awk.getX();
						if(Link2.equals(Link3)) {
							foundPDF.add(new Pair<>(Link,Title));
						}
					}
				}
				
			}
		}
		return foundPDF;
	}
	
}
