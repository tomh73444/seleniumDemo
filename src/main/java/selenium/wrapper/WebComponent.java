package selenium.wrapper;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebComponent  {
		
	private By by;
	private String name;
	private WebDriver driver;
	public WebComponent(WebDriver driver) {
		this.driver=driver;
	}
	public WebComponent(String name,By by) {
		this.by=by;
		this.name=name;
		
	}
	
	public WebElement findElement() {
		WebElement we = null;
		try {
		we= driver.findElement(by);
		}
		
		catch(Exception e){
			System.out.println("Element "+name+" not found");
			e.printStackTrace();
		}
		return we;
		
	}
	
	public List<WebElement> findElements() {
		List<WebElement> we = null;
		try {
		we= driver.findElements(by);
		}
		
		catch(Exception e){
			System.out.println("Element "+name+" not found");
			e.printStackTrace();
		}
		return we;
		
	}
	

	
}
