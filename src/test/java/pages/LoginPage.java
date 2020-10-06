package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import selenium.data.LoginInfo;

public class LoginPage {
	private WebDriver driver;
	By inputUserName= By.id("username");
	By inputPassword= By.id("password");
	By btnLogin= By.cssSelector("button[type='submit']");
	By msg= By.id("flash");

	public LoginPage(WebDriver driver) {
		this.driver=driver;
	}
	
	public void visit(String url) {
		driver.navigate().to(url);
	}
	
	public void enterCredentials(String username,String password) {
	
	driver.findElement(inputUserName).clear();
	driver.findElement(inputUserName).sendKeys(username);
	driver.findElement(inputPassword).clear();
	driver.findElement(inputPassword).sendKeys(password);
	}
	
	public void submit() {
		driver.findElement(btnLogin).click();
	  
	}
	
	public String getMessgae() {

	return driver.findElement(msg).getText().trim();

	}
	
}
