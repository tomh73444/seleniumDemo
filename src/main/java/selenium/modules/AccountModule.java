package selenium.modules;

import org.openqa.selenium.WebDriver;

import selenium.data.LoginInfo;
import selenium.pages.LoginPage;

public class AccountModule {

	private LoginPage accountPage;
	public AccountModule(WebDriver driver) {
		// TODO Auto-generated constructor stub
		accountPage=new LoginPage(driver);
	}
	/**
	 * Takes a username and password, fills out the fields, and clicks "login".
	 * @return An instance of the AccountPage
	 */
	public AccountModule loginAsUser(LoginInfo info) {
		accountPage.enterCredentials(info);
	  return this;
	}
	
	

}
