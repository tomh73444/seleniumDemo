package selenium.wrapper;

import org.openqa.selenium.By;

public class Button extends WebComponent{
	public Button(String name, By by) {
		super(name,by);
	}
	
	public Button click() {
		
		findElement().click();
		
		return this;
	}
}
