package step;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class GuardianStepClass {

	public static RemoteWebDriver driver;
	public String gaurdianArticleTitle,gaurdianArticleUrl,secondWebSiteUrl, guardianSite = "www.theguardian.com";
	public JavascriptExecutor js;

	@Given("Open Browser as {string}")
	public void open_browser_as(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			js = (JavascriptExecutor) driver;
		}

		else if (browser.equalsIgnoreCase("firefox")) {

			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			js = (JavascriptExecutor) driver;
		}

		else if (browser.equalsIgnoreCase("edge")) {

			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			js = (JavascriptExecutor) driver;
		}
	}

	@Given("Load Guardian Website as {string}")
	public void load_guardian_website_as(String url) {
	    driver.get(url);
	}

	@Given("Click Top Headline News")
	public void click_top_headline_news() throws InterruptedException {
		Thread.sleep(5000);
		driver.switchTo().frame(driver.findElementByXPath("//iframe[@title='SP Consent Message']"));
	    if(driver.findElementByXPath("//div[@class='message-component message-row gu-content gs-container']").isEnabled())
	    {
	    	driver.findElementByXPath("//button[@title='Yes, I’m happy']").click();
	    	driver.switchTo().parentFrame();
	    }
	    js.executeScript("arguments[0].click()",  driver.findElementByXPath("(//div[@class='fc-item__content ']//a)[1]//*[@class='js-headline-text']"));
	    //driver.findElementByXPath("(//div[@class='fc-item__content ']//a)[1]//*[@class='js-headline-text']").click();
	    
	}

	@Then("Top Headline news should open")
	public void top_headline_news_should_open() {
	   if(driver.findElementByXPath("//div[@class='css-1uiaxdu']").isEnabled())
	   {
		   driver.findElementByXPath("(//div[@class='css-xfiylr']//button[@aria-label='Close']/span[text()='Close']/following::*)[1]").click();
	   }
	   
	   gaurdianArticleTitle = driver.findElementByXPath("(//h1)[1]").getText();
	   gaurdianArticleUrl = driver.getCurrentUrl();
	}

	@Given("Go to Google")
	public void go_to_google() {
		WebDriver newWindow = driver.switchTo().newWindow(WindowType.TAB);
		newWindow.get("https://www.google.com/");
		
	
	}

	@Then("Search the Top Headline news from Guardian")
	public void search_the_top_headline_news_from_guardian() {
		driver.findElementByXPath("//input[@role='combobox']").sendKeys(gaurdianArticleTitle);
		driver.findElementByXPath("//input[@role='combobox']").sendKeys(Keys.ENTER);
		driver.findElementByXPath("//a[@class='hide-focus-ring' and @data-hveid='CAEQAw']").click();
	}

	@Then("Look for other website article with same article title")
	public void look_for_other_website_article_with_same_article_title() {
	    
		secondWebSiteUrl = driver.findElementByXPath("(//div[@class='dbsr']//a)[2]").getAttribute("href");
		
	}

	@Then("Print Guardian News is Valid")
	public void print_guardian_news_is_valid() {
	    
		if(!secondWebSiteUrl.contains(guardianSite)) {
			System.out.println("Article is Valid");
		}
		
		else {
			System.out.println("Article is not Valid");
		}
	}

}
