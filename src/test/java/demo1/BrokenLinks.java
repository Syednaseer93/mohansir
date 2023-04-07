package demo1;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrokenLinks {
	@Test
	public  void getBrokenLinksAndCount() throws IOException   {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("remote-allow-origins=*");
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver(options);
		driver.get("http://www.phptravels.com");
		List<String>allLinks= new ArrayList<String>();
		List<String>brokenLinks= new ArrayList<String>();
		List <WebElement> links= driver.findElements(By.tagName("a"));
		for(WebElement ele:links)
		{		
			String url=ele.getAttribute("href");
			if(url!=null )
			{
				if(url.contains("http")) {
					allLinks.add(url+" ===> Not having HTTP Protocol");
				}
				else {
					brokenLinks.add(url+" ===> Null");
				}
			}
			for(String link:allLinks) {
				try {
					URL finalLink = new URL(link);
					HttpURLConnection httpCon = (HttpURLConnection) finalLink.openConnection();
					httpCon.connect();
					int statusCode=httpCon.getResponseCode();
					String msg=httpCon.getResponseMessage();
					if(statusCode>=400) {
						brokenLinks.add(url+" Status Code is: "+statusCode+" and Response messageis :"+msg);
					}
				}
				catch(Exception e) {
					brokenLinks.add(url+" ===> Not connected to server");
				}
			}			
		}
		System.out.println(brokenLinks);
		System.out.println(brokenLinks.size());
	}
}