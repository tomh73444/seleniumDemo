package stepdefs;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import hooks.ScenarioHooks;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.DashboardPage;
import pages.LoginPage;

public class LoginStepDefintions {
	
private	LoginPage loginPage;
private	DashboardPage dashboarPage;
private	WebDriver driver;
private Scenario scenario;

@Given("User should be on the loginpage")
public void user_should_be_on_the_loginpage(List<String> urls) {
	
		for (int i = 0; i < urls.size(); i++) {
			System.out.print(urls.get(i));
		}
		
		driver=ScenarioHooks.driver;
		scenario=ScenarioHooks.scenario;
		loginPage=new LoginPage(driver);
		loginPage.visit(urls.get(0));
		scenario.log("navigated to url"+ urls.get(0));
		
}

@When("User submits his {string} and {string}")
public void user_submits_his_and(String username, String password) {
		
	loginPage.enterCredentials(username,password);
	loginPage.submit();
	scenario.log("submitted credentials"+ username + password);
	
}

@Then("User should see the message {string}")
public void user_should_see_the_message(String msg) {
		
	String msgFromUI=loginPage.getMessgae();
	Assert.assertTrue(msgFromUI.contains(msg));
	scenario.log("msg from ui validated : "+msgFromUI);
			
    
}

@Then("User logs out of the secure area")
public void user_logs_out_of_the_secure_area() {
		dashboarPage=new DashboardPage(driver);
		dashboarPage.logout();
		scenario.log("logout from app");
}

}
