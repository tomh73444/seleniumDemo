package hooks;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class ScenarioHooks {
	
	public static WebDriver driver;
	public static Scenario scenario;
	@Before()
	public void beforeScenario(Scenario scenario) {
		ScenarioHooks.scenario=scenario;
		driver =new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		scenario.log("before scenario : "+scenario.getName() );
	}

	@After()
	public void afterScenario(Scenario scenario) {
				
		if(driver!=null)
		{driver.quit();
		}
		
		scenario.log("after scenario : "+scenario.getName() );
	}
	
}
