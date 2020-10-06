package demo;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SeleniumDemo3 {
	private WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;
	private DateTimeFormatter formatter;

	@BeforeMethod
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver");
		this.driver = new ChromeDriver();
		//implicit timeout
		this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		// for js async actions script timeout
		this.driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
		
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.driver.manage().window().maximize();
		this.actions = new Actions(driver);
		formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}


	@Test(enabled = false)
	public void jsExecutor() {
		System.out.println("sync javascript");

		driver.navigate().to("https://the-internet.herokuapp.com/login");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		// sync
		System.out.println(formatter.format(LocalDateTime.now()));
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		js.executeScript("window.setTimeout(function(){ alert(\"Hello\"); }, 5000);");
		
		System.out.println(formatter.format(LocalDateTime.now()));
		
		Alert alert = driver.switchTo().alert();
//	Alert alert= wait.until(ExpectedConditions.alertIsPresent()); // explicitly waiting for the alert
		alert.accept();

	}

	@Test(enabled = false)
	public void jsAsyncExecutor() {
		System.out.println("Async javascript");
		driver.navigate().to("https://the-internet.herokuapp.com/login");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		// async
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		System.out.println(formatter.format(LocalDateTime.now()));
		
		js.executeAsyncScript("window.setTimeout(function(){ alert(\"Async Hello\"); }, 5000);");
		
		System.out.println(formatter.format(LocalDateTime.now()));
		Alert alert = driver.switchTo().alert(); // no wait is required to accept the alert as jvm understands that
													// async is going on
		alert.accept();
	}
	

	@Test(enabled = false)
	public void otherJSActions() {

		By password = By.id("password");
		By username = By.id("username");
		By message = By.cssSelector("div#flash");
		By dragAndDrop = By.cssSelector("a[href*='drag_and_drop']");

		String loginMsg = "You logged into a secure area!";

		driver.navigate().to("https://the-internet.herokuapp.com/login");
		wait.until(ExpectedConditions.visibilityOfElementLocated(username));

		// find element using jse
		JavascriptExecutor js = (JavascriptExecutor) driver;
	
		Object searchTextbar = js.executeScript("return document.getElementById('username')"); //jse 
		
		((WebElement) searchTextbar).sendKeys("tomsmith"); //not sending text usng js
		
		actions.pause(Duration.ofSeconds(3)).build().perform();

		// enter text
		js.executeScript("arguments[0].value='SuperSecretPassword!'", driver.findElement(password));

		// click
		js.executeScript("document.querySelector('button>i.fa-sign-in').click()"); // use query selector to pass css

		wait.until(ExpectedConditions.visibilityOfElementLocated(message));

		// get the text

		String flashMsg = (String) js.executeScript("return document.getElementById('flash').innerText");
		
		System.out.println(flashMsg);
		
		Assert.assertTrue(flashMsg.contains(loginMsg));
		
		actions.pause(Duration.ofSeconds(3)).build().perform();

		// highlight an element by changing style
		wait.until(ExpectedConditions.visibilityOfElementLocated(message));
		WebElement msgEle = driver.findElement(message);
		js.executeScript("arguments[0].style='background: yellow; border: 2px solid red';", msgEle);
		actions.pause(Duration.ofSeconds(3)).build().perform();

		driver.navigate().to("https://the-internet.herokuapp.com/");

		// scroll
		// scrollBy takes in horizontal and vertical px
		js.executeScript("window.scrollBy(0,500)");
		
		actions.pause(Duration.ofSeconds(3)).build().perform();

		// only use this when required, as selenium generally will scroll to element and
		// perform the action
		WebElement dragEle = driver.findElement(dragAndDrop);
		
		js.executeScript("arguments[0].scrollIntoView(true)", dragEle);
		actions.pause(Duration.ofSeconds(3)).build().perform();

		// return the title of the page
		String title = (String) js.executeScript("return document.title");
		System.out.println("document title : " + title);

		// maximum scroll distance vertically
		Long scrollmaxY = (Long) js.executeScript("return window.scrollMaxX");
		System.out.println("max scroll Y :" + scrollmaxY);

		// maximum scroll distance Horizontally
		Long scrollmaxX = (Long) js.executeScript("return window.scrollMaxX");
		System.out.println("max scroll X : " + scrollmaxX);

		// height of the webpage
		Long innerHeight = (Long) js.executeScript("return window.innerHeight");
		System.out.println("inner height : " + innerHeight);

		// width of the webpage
		Long innerWidhth = (Long) js.executeScript("return window.innerWidth");
		System.out.println("inner width : " + innerWidhth);

		// return the state of the page load
		String state = (String) js.executeScript("return document.readyState");
		System.out.println("document state : " + state);

	}
	

	@Test(enabled = false)
	public void robotSample() {
		// java robot class example
		try {

			
			driver.navigate().to("https://the-internet.herokuapp.com/jqueryui/menu");
			By menu1 = By.linkText("Enabled");
			By menu2 = By.linkText("Downloads");
			By menu3 = By.linkText("PDF");
			int PAUSETIME = 2;

			wait.until(ExpectedConditions.visibilityOfElementLocated(menu1));

			actions.moveToElement(driver.findElement(menu1)).pause(Duration.ofSeconds(PAUSETIME)).build().perform();

			actions.moveToElement(driver.findElement(menu2)).pause(Duration.ofSeconds(PAUSETIME)).build().perform();

			actions.moveToElement(driver.findElement(menu3)).pause(Duration.ofSeconds(PAUSETIME)).click().build().perform();

			actions.pause(Duration.ofSeconds(5)).build().perform();;
			// create object to robot class
			Robot robot = new Robot();
			
			robot.delay(5000);
			
			//capture screeshot
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			// capture screen of the desktop
			BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
			// save the screenshot to local system
			ImageIO.write(screenFullImage, "png", new File("./screenshots/FullScreenshotRobot.png"));
	
			//capture certain are on the webpage
			Rectangle area = new Rectangle(10, 10, 500, 200);
			BufferedImage bufferedImage = robot.createScreenCapture(area);
			ImageIO.write(bufferedImage, "png", new File("./screenshots/AreaScreenshot.png"));

			
			// press tab first time
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.delay(2000);
			robot.keyRelease(KeyEvent.VK_DOWN);
			robot.delay(2000);
			// press tab second time
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.delay(2000);
			robot.keyRelease(KeyEvent.VK_DOWN);
			robot.delay(2000);
			// press enter key
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.delay(2000);
			robot.keyRelease(KeyEvent.VK_ENTER);
			actions.pause(Duration.ofSeconds(5)).build().perform();;

		} catch (AWTException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
