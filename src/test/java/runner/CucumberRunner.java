package runner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.presentation.PresentationMode;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "./src/test/resources/features/Login.feature",
		glue = {"stepdefs","hooks"},
		tags = "@valid_login",
		plugin = {"pretty","html:target/cucumber","json:target/cucumber.json"})
public class CucumberRunner {
		
	
	@BeforeClass
	public static void setup() {
		System.setProperty("webdriver.gecko.driver", "./src/test/resources/drivers/geckodriver");
	}
	
	@AfterClass
	public static void generateReport() {
		File reportOutputDirectory = new File("target");
		List<String> jsonFiles = new ArrayList<>();
		jsonFiles.add("./target/cucumber.json");
		
		String buildNumber = "1";
		String projectName = "SamplecucumberProject";

		Configuration configuration = new Configuration(reportOutputDirectory, projectName);
		// optional configuration - check javadoc for details
		configuration.addPresentationModes(PresentationMode.RUN_WITH_JENKINS);
		configuration.setBuildNumber(buildNumber);
		// addidtional metadata presented on main page
		configuration.addClassifications("Platform", "MAC");
		configuration.addClassifications("Browser", "Firefox");
		configuration.addClassifications("Branch", "release/1.0");

		ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
		Reportable result = reportBuilder.generateReports();
	}

}
