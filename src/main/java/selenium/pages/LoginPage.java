package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import selenium.data.LoginInfo;
import selenium.wrapper.BasePage;

public class LoginPage extends BasePage {

	private String path="/login";
	private WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}
	
	public LoginPage visit() {
		navigate(getURL()+path);
		return this;
	}

	By inputUserName= By.id("username");
	By inputPassword= By.id("password");
	By btnLogin= By.cssSelector("button[type='submit']");
//	InputField inputUserName = new InputField("user name field",By.id("loginField"));
//	InputField inputPassword = new InputField("password field",By.id("loginField"));
//	Button btnLogin = new Button("login button",By.id("loginField"));

	
	public LoginPage enterCredentials(LoginInfo loginInfo) {
//		  inputUserName.clear().enterText(loginInfo.getUsername());
//		  inputPassword.clear().enterText(loginInfo.getUsername());
		
	driver.findElement(inputUserName).clear();
	driver.findElement(inputUserName).sendKeys(loginInfo.getUsername());;
	driver.findElement(inputPassword).clear();
	driver.findElement(inputPassword).sendKeys(loginInfo.getPassword());;
	
	return this;
	}
	
	public LoginPage submit() {
//		btnLogin.click();
		driver.findElement(btnLogin).click();
		return this;  
	}
	
	

}
