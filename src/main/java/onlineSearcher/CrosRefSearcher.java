package onlineSearcher;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import Utilities.ProgressUpdater;

public class CrosRefSearcher {

	static String text="";
	public static void search(String doi) throws IOException {
		
		Thread t2 = new Thread(new ProgressUpdater());
		t2.start();
		
		ChromeOptions options = new ChromeOptions();
    	options.addArguments("--headless", "--window-size=1920,1200");
		
    	//"10.1093/BIB/BBAB403"
    	
        WebDriver driver = new ChromeDriver(options);
        //driver.get("https://selenium.dev");
        driver.get("https://www.crossref.org/guestquery/");
        
        ProgressUpdater.updateBarr(20);
  
        driver.findElement(By.name("doi")).sendKeys(doi) ;
        driver.findElement(By.name("doi_search")).submit();
        
        ProgressUpdater.updateBarr(40);
        
        String result = driver.findElement(By.name("xml")).getText();
        
        ProgressUpdater.updateBarr(60);
        try {
        int end= result.indexOf("<doi_record>");
        result = result.replace(result.subSequence(0, end), "");
        }catch(Exception e) {}
        
        ProgressUpdater.updateBarr(80);
        
        try {
        int start= result.indexOf("</doi_record>");
        result = result.replace(result.subSequence(start+13, result.length()), "");  
        }catch(Exception e) {};
        
        driver.quit();
        ProgressUpdater.updateBarr(100);
      
        try {
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        setText(result);
	}

	public static void setText(String result) {
		text = result;
	}
	
	public static String getText() {
		return text;
	}

}
