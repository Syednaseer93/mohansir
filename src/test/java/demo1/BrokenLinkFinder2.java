package demo1;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrokenLinkFinder2 {
	@Test
	public void test() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("remote-allow-origins=*");
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://phptravels.com");
		List<WebElement> listofalllinks = driver.findElements(By.xpath("//a"));
		List<String> listoflinks=new ArrayList<String>();
		List<String> brokenlinks=new ArrayList<String>();
		for (  WebElement webelement : listofalllinks) {
			String link = webelement.getAttribute("href");
			if(link!=null) {
				if(link.contains("http")) {
					listoflinks.add(link);
				}
				else {
					brokenlinks.add(link+" ===>not having http protocol");
				}
			}
			else {
				brokenlinks.add(link+"===>  null");
			}
		}
		for (String link : listoflinks) {
			try {
				URL url=new URL(link);
				URLConnection urlcon=url.openConnection();
				HttpURLConnection httpurlcon=(HttpURLConnection) urlcon;
				int statuscode=httpurlcon.getResponseCode();
				String resmassage = httpurlcon.getResponseMessage();
				if(statuscode>=400) {
					brokenlinks.add(link+" ===>statuscode: "+statuscode+"===>responsemsg :"+resmassage);
				}
				
			}catch (Exception e) {
				brokenlinks.add(link+"  ====>not connected to server");
			}
		}
       System.out.print(brokenlinks);
       System.out.println(brokenlinks.size());
       driver.close();
	}

}
