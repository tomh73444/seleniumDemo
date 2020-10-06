package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage  extends LoginPage{
	private WebDriver driver;

	By logoutbtn= By.cssSelector("a[href*='logout']");


	public DashboardPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}

	
	public void logout() {
		driver.findElement(logoutbtn).click();
	  
	}
}
