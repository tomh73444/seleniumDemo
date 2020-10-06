package selenium.wrapper;

import org.openqa.selenium.WebDriver;

public class DriverFactory {
	
	private WebDriver driver;


	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	

}
