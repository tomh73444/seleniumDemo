package demo;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Sample {
	private WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;
	
	@BeforeMethod
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver");
		this.driver=new ChromeDriver(); 
		this.driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		//explicit wait
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.driver.manage().window().maximize();
	    this.actions=new Actions(driver);
	}
	
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	// normal login and checkbox
	@Test(enabled=false)
	public void login() {
		
		driver.navigate().to("https://the-internet.herokuapp.com/login");
		
		By loginForm=By.cssSelector("form#login");
		By usernameInput=By.cssSelector("input#username");
		By passwordInput=By.cssSelector("input#password");
		By loginBtn=By.cssSelector("button>i.fa-sign-in");
		By message=By.cssSelector("div#flash");
		By signout=By.cssSelector("a>i.icon-signout");
		
		String loginMsg="You logged into a secure area!";
		String logoutMsg="You logged out of the secure area!";
		
		//different expected conditions, visibility and presence 
		wait.until(ExpectedConditions.visibilityOfElementLocated(loginForm));
		
		WebElement usrName = driver.findElement(usernameInput);
		
		
		usrName.sendKeys("tomsmith");
		
		//element is displayed or not
		boolean b=usrName.isDisplayed();
		System.out.println(b);
		
		 // except for disabled elements this wil return true
		b=usrName.isEnabled(); 
		System.out.println(b);
		
		
		
		driver.findElement(passwordInput).sendKeys("SuperSecretPassword!");
		driver.findElement(loginBtn).click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(message));
		String msg=driver.findElement(message).getText().trim();
		
		Assert.assertTrue(msg.contains(loginMsg));
		
		driver.findElement(signout).click();
		 msg=driver.findElement(message).getText();
		Assert.assertTrue(msg.contains(logoutMsg));
		
		
		driver.navigate().to("https://the-internet.herokuapp.com/checkboxes");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.example>h3")));
		
		List<WebElement> checkboxes=driver.findElements(By.cssSelector("form#checkboxes>input"));
		// applies to checkboxes, options in a select and radio buttons, 
			//returns weather the element is selected or not
		takeScreenshot("before checkbox");
		
		for (WebElement webElement : checkboxes) {
			b=webElement.isSelected(); 
			System.out.println(b);
			if(b==false) {
				webElement.click();
			}
		}	
		takeScreenshot("after checkbox");
		
	}
	
	
	@Test(enabled = false)
	public  void alerts() {
		
		
		
		driver.navigate().to("https://the-internet.herokuapp.com/javascript_alerts");		
			
		WebElement alertBtn = driver.findElement(By.xpath("//button[contains(text(),'Alert')]"));
		WebElement confirmBtn = driver.findElement(By.xpath("//button[contains(text(),'Confirm')]"));
		WebElement promptBtn = driver.findElement(By.xpath("//button[contains(text(),'Prompt')]"));
			

		//Click the link to activate the alert
		alertBtn.click();

		//Wait for the alert to be displayed and store it in a variable
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		//Store the alert text in a variable
		String text = alert.getText();
		Reporter.log("Alert Text : "+text);
		
		//Press the OK button
		alert.accept();
		  
		
		//Click the link to activate the alert
		confirmBtn.click();

		//Wait for the alert to be displayed
		wait.until(ExpectedConditions.alertIsPresent());

		//Store the alert in a variable
		Alert confirm = driver.switchTo().alert();

		//Store the alert in a variable for reuse
		String text2 = confirm.getText();
		
		Reporter.log("Confirm Text : "+text2);
		//Press the Cancel button
		confirm.dismiss();
		  
		

		//Click the link to activate the alert
		promptBtn.click();
		//Wait for the alert to be displayed and store it in a variable
		Alert prompt = wait.until(ExpectedConditions.alertIsPresent());
		//Type your message
		String promptText="Selenium";
		prompt.sendKeys(promptText);
		//Press the OK button
		prompt.accept();
		
		WebElement result = driver.findElement(By.cssSelector("p#result"));
		String resultValue= result.getText();
		Assert.assertTrue(resultValue.contains(promptText));
		  
	}	
	
	
	@Test(enabled = false)
	public void captureScreenshot()
	{
		
        driver.get("https://the-internet.herokuapp.com/");
        takeScreenshot("page screenshot");
		
	}
	
	//selenium 4 feature
	@Test(enabled = false)
	public void captureElementScreenshot()
	{
        driver.get("https://www.makemytrip.com/");
        WebElement element = driver.findElement(By.cssSelector("div.aboutMMT"));        
        takeScreenshot(element,"element screenshot");
	       
	}	
	
	//mouse & keyboard actions
	//click,doubleclick,keyup,keydown,contextclick,movetoElement,moveToOffset
	@Test(enabled = false)
	public void actionsAPI() throws InterruptedException
	{
		
		
		//move to element
		
		driver.get("https://the-internet.herokuapp.com/");
		WebElement keyPressLink=driver.findElement(By.linkText("Key Presses"));
		
		actions.moveToElement(keyPressLink).pause(Duration.ofSeconds(5)).click().build().perform();
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#target")));
		//actions can be performed drirectly without the help of a webelement
		WebElement input=driver.findElement(By.cssSelector("input#target"));	
		actions.sendKeys(Keys.TAB)
		.keyDown(Keys.SHIFT).sendKeys(input,"key press").pause(Duration.ofSeconds(5))
		.keyUp(Keys.SHIFT).build().perform();
		
		input.clear(); //will clear the text inside the editable input element
		// will throw invalid element state (or) Element not interactable
		
		//Browser navigation
//		driver.navigate().back(); 
//		driver.navigate().forward();
//		driver.navigate().refresh();
//		driver.navigate().to("url");
		
//        driver.get("https://the-internet.herokuapp.com/drag_and_drop");
//
//		WebElement srcEle=driver.findElement(By.id("column-a"));
//		
//		WebElement trgtEle=driver.findElement(By.id("column-b"));
//		
//        takeScreenshot("before drag and drop");
//        actions.dragAndDrop(srcEle, trgtEle).perform();
//        
//        takeScreenshot("after drag and drop");
//   
//        //drag & drop using location
//        
//        actions.dragAndDropBy(trgtEle,srcEle.getLocation().getX(),
//        		srcEle.getLocation().getY()).perform();
//        takeScreenshot("after drag and drop using by");
	}
	

	@Test(enabled = false)
	public void jqueryMenu() {
		//type conversion ofSeconds
		
		driver.navigate().to("https://the-internet.herokuapp.com/jqueryui/menu");
		By menu1=By.linkText("Enabled");
		By menu2=By.linkText("Downloads");
		By menu3=By.linkText("PDF");
		int PAUSETIME=3;
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(menu1));
	
		actions.moveToElement(driver.findElement(menu1))
		.pause(Duration.ofSeconds(PAUSETIME)).build().perform();
		
		actions.moveToElement(driver.findElement(menu2))
			.pause(Duration.ofSeconds(PAUSETIME)).build().perform();
		
		actions.moveToElement(driver.findElement(menu3))
			.pause(Duration.ofSeconds(PAUSETIME)).build().perform();
			
		takeScreenshot("Menu");
		
	}
	
	@Test(enabled = false)
	public void selectAPI() {
		
		driver.navigate().to("https://the-internet.herokuapp.com/dropdown");
		By dropDown=By.id("dropdown");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(dropDown));
		
		// works only on select tag elements not on div or other elements
		Select selectEle= new Select(driver.findElement(dropDown));
		
		selectEle.selectByVisibleText("Option 2");
		selectEle.selectByValue("1");
		selectEle.selectByIndex(2);
		
		List<WebElement> options=selectEle.getOptions();// returns all the available options in the select dropdown
		WebElement we = selectEle.getFirstSelectedOption(); //retuns the currently selected option
		List<WebElement> selectedOptions = selectEle.getAllSelectedOptions(); //retuns the all selected options
		
		selectEle.deselectByIndex(0);
		selectEle.deselectByValue("Option 1");
		selectEle.deselectAll(); // in case of multiple select is supported in dropdown
	
		//to check if the select dropdown allow multiple selection
		Boolean doesThisAllowMultipleSelections = selectEle.isMultiple(); 

	}
	
	
	@Test(enabled = false)
	public void windowHandle() {
		
		driver.navigate().to("https://the-internet.herokuapp.com/windows");
		By clickHere=By.linkText("Click Here");
		By parentWinInfo=By.cssSelector("div.example>h3");
		By newWinInfo=By.cssSelector("div.example>h3");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(clickHere));
		String parentWindow=driver.getWindowHandle(); // current window handle
		
		
		driver.findElement(clickHere).click();
		//get all the windows opened using window handles
		Set<String> windows=driver.getWindowHandles();
		
		Iterator<String> it=windows.iterator();
		
		System.out.println(it.next());
		
		driver.switchTo().window(it.next());
		
		actions.pause(Duration.ofSeconds(3)).perform();
		
		WebElement element=driver.findElement(newWinInfo);
		String text = element.getText();
		Assert.assertTrue(text.equalsIgnoreCase("New Window"));
		
		driver.close();  // closes the current window
		
		driver.switchTo().window(parentWindow);  // switching focus back to parent window
		
		actions.pause(Duration.ofSeconds(3)).perform();
		
		WebElement element2=driver.findElement(parentWinInfo);
		String text2 = element2.getText();
		Assert.assertTrue(text2.equalsIgnoreCase("Opening a new window"));
		

		
		
		
	}
	
	
	@Test(enabled = false)
	public void webtable()
	{
		driver.navigate().to("https://the-internet.herokuapp.com/tables");
		
		// if no id's are available for the web table 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("table1")));
		List<WebElement> rows=driver.findElements(By.cssSelector("table#table1>tbody tr"));
		
		for (WebElement rowEle : rows) {
			List<WebElement> columns=rowEle.findElements(By.cssSelector("td"));
			String rowData="";
			for (WebElement colEle : columns) {
				rowData += colEle.getText()+" ";
				
			}
			System.out.println(rowData);
			Reporter.log(rowData);
		}
		
		//if id's are present
		List<WebElement> emailsEle=driver.findElements(By.cssSelector("table#table2 td.email"));
		List<WebElement> lastNameEle=driver.findElements(By.cssSelector("table#table2 td.last-name"));
		List<WebElement> firstNameEle=driver.findElements(By.cssSelector("table#table2 td.first-name"));
		List<WebElement> duesEle=driver.findElements(By.cssSelector("table#table2 td.dues"));
		List<WebElement> websiteEle=driver.findElements(By.cssSelector("table#table2 td.web-site"));
		
		List<Person> details=new ArrayList<>();
		
		
		for (int i=0;i<firstNameEle.size();i++) {
			
			String lastname  = 		lastNameEle.get(i).getText();
		    String firstname = 		firstNameEle.get(i).getText();
			String email   =		emailsEle.get(i).getText();
			String dues      =		duesEle.get(i).getText().replace("$", "");
			String website   =		websiteEle.get(i).getText();
				
				Person person=new Person();
				person.setLastName(lastname);
				person.setFirstName(firstname);
				person.setEmail(email);
				person.setDues(Double.parseDouble(dues));
				person.setWebsite(website);
				details.add(person);
				System.out.println(person.toString());
				Reporter.log(person.toString());
		}
		
	}
	
	
	@Test(enabled=false)
	public void color() {
		Color loginButtonColour = Color.fromString(driver.findElement(By.id("login")).getCssValue("color"));
		Color loginButtonBackgroundColour = Color.fromString(driver.findElement(By.id("login")).getCssValue("background-color"));
		Assert.assertTrue(loginButtonBackgroundColour.asHex().equals("#ff69b4"));
		Assert.assertTrue(loginButtonBackgroundColour.asRgba().equals("rgba(255, 105, 180, 1)"));
		Assert.assertTrue(loginButtonBackgroundColour.asRgb().equals("rgb(255, 105, 180)"));
	}
	
	
	
	@Test(enabled=false)
	public void horizontalSlider() {
		
		//using actions
		By slider=By.cssSelector("div.sliderContainer>input");
		By range=By.cssSelector("span#range");
		
		driver.navigate().to("https://the-internet.herokuapp.com/horizontal_slider");
		wait.until(ExpectedConditions.visibilityOfElementLocated(slider));
		
		WebElement sliderEle=driver.findElement(slider);
		
		int sliderWidth = sliderEle.getSize().getWidth();
	
		
		int xCoord = sliderEle.getLocation().getX();
		
		Actions builder = new Actions(driver);   
		builder.moveToElement(sliderEle)
			   .click()
			   .dragAndDropBy
			     (sliderEle,xCoord + sliderWidth, 0).pause(Duration.ofSeconds(3))
			   .build()
			   .perform();
		
		
		//using keys
		double k=Double.valueOf(driver.findElement(range).getText());
		
		while(k>0) {
		sliderEle.sendKeys(Keys.ARROW_LEFT);
		 k=Double.valueOf(driver.findElement(range).getText());
		 actions.pause(Duration.ofSeconds(1)).perform();;
		}
		
	
		
	}
	
	//frames
	@Test(enabled=false)
	public void framesHandling() {
		By framesEle=By.linkText("Nested Frames");
		driver.navigate().to("https://the-internet.herokuapp.com/frames");
		wait.until(ExpectedConditions.elementToBeClickable(framesEle));
		driver.findElement(framesEle).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("frameset")));
		
		String text="";
		//index, name, id, element
		
		driver.switchTo().frame("frame-top");
		driver.switchTo().frame("frame-left");
		
		text=driver.findElement(By.tagName("body")).getText();
		System.out.println(text);
		
		driver.switchTo().parentFrame();
		
		driver.switchTo().frame("frame-middle");

		text=driver.findElement(By.tagName("body")).getText();
		System.out.println(text);
		
		driver.switchTo().parentFrame();
		driver.switchTo().frame("frame-right");
		text=driver.findElement(By.tagName("body")).getText();
		System.out.println(text);
		
		// to come out of all frames switchto.defaultcontent
		driver.switchTo().defaultContent();
		
		
		//or wait for the frame and switch to it
		//can provide frame id,name,index,locator,webelement
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("frame-bottom"));
		
		text=driver.findElement(By.tagName("body")).getText();
		System.out.println(text);
		
		driver.switchTo().defaultContent();
		
		
		
	}
	
	
	public void takeScreenshot(String fileName) {
		try {
	
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		 FileUtils.copyFile(scrFile, new File("./screenshots/"+fileName+".png"));
		 }
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void takeScreenshot(WebElement element,String fileName) {
		try {
		File scrFile = element.getScreenshotAs(OutputType.FILE);
		 FileUtils.copyFile(scrFile, new File("./screenshots/"+fileName+".png"));
		 }
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
