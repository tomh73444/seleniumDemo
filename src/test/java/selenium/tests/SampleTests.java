package selenium.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import selenium.data.LoginInfo;
import selenium.pages.LoginPage;


public class SampleTests {
	private WebDriver driver;
	private LoginInfo loginInfo;
	private String url="https://the-internet.herokuapp.com/";
	

	@BeforeMethod
	public void setup() {
		// preferably set the drivers path in system environment variables or use webdrivermanager
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver");	
        driver = new ChromeDriver();
		loginInfo=new LoginInfo("tomsmith","SuperSecretPassword!");
		driver.navigate().to(url);
		
	}
	
	@AfterMethod
	public void cleanup() {
		//closing the browser
		driver.quit();
	}
	
	@Test
	public void loginTest() {
			
		LoginPage loginPage=new LoginPage(driver);
		loginPage.visit().enterCredentials(loginInfo).submit();
		Reporter.log("login successful");
		
	}

}
