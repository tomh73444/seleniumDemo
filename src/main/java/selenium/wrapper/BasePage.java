package selenium.wrapper;

import org.openqa.selenium.WebDriver;

public class BasePage{
	private WebDriver driver;

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}
	

	public String getURL() {
		return driver.getCurrentUrl();
	}
	
	public void navigate(String url) {
		driver.navigate().to(url);
	}
	
	
	public void refresh() {
		driver.navigate().refresh();
	}

}
