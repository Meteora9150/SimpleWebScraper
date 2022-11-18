package onlineSearcher;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class FileDownloader_NOT_Working {

	static InputStream input = null;
    static OutputStream output = null;
    static HttpURLConnection connection = null;
	
    public static void main(final String[] args) {
    	DownloadFile();
    }
    
    public static String DownloadFile() {
    try {
    	System.out.println("blocco 0");  
        URL url = new URL("https://www.mapa.gob.es/ministerio/pags/biblioteca/revistas/pdf_Vrural/Vrural_2005_213_16_20.pdf");
        System.out.println("blocco 0.5");  
        connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        System.out.println("blocco 1");        
        // expect HTTP 200 OK, so we don't mistakenly save error report
        // instead of the file
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
        	System.out.println("connection error");
            return "Server returned HTTP " + connection.getResponseCode()
                    + " " + connection.getResponseMessage();
        }
        System.out.println("blocco 2");  
        // this will be useful to display download percentage
        // might be -1: server did not report the length
        int fileLength = connection.getContentLength();

        // download the file
        input = connection.getInputStream();
        output = new FileOutputStream("C:\\Prova.txt");

        byte data[] = new byte[4096];
        int count=input.read(data);
        System.out.println("data = "+data+"\ncount = "+count);
        while ((count = input.read(data)) != -1) {
        	System.out.println("data = "+data+"\ncount = "+count);
            output.write(data, 0, count);
        }
    } catch (Exception e) {
    	System.out.println("exception E?");
        return e.toString();
    } finally {
        System.out.println("terminato ok?");
        try {
            if (output != null)
                output.close();
            if (input != null)
                input.close();
        } catch (IOException ignored) {
        }

        if (connection != null)
            connection.disconnect();
    }
	return null;
    }
}
