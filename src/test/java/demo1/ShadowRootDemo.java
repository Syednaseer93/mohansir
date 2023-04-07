package demo1;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ShadowRootDemo {
   @Test
	public void search() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("remote-allow-origins=*");
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("chrome://downloads/");
		
		JavascriptExecutor js =(JavascriptExecutor) driver;
		WebElement search =(WebElement)js.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('downloads-toolbar').shadowRoot.querySelector('cr-toolbar').shadowRoot.querySelector('#search').shadowRoot.querySelector('input')");
		search.sendKeys("Mohan");
		
		/*js command is below
		 * 
		 *document.querySelector('downloads-manager').shadowRoot
		 .querySelector('downloads-toolbar') .shadowRoot
		 .querySelector('cr-toolbar').shadowRoot  
		 .querySelector('#search').shadowRoot.querySelector('input')
		 */
	}
}
