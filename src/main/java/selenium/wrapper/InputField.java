package selenium.wrapper;

import org.openqa.selenium.By;

public class InputField extends WebComponent{
	
	
	public InputField(String name, By by) {
		super(name,by);
	}
	
	public InputField clear() {
		
		findElement().clear();
		return this;
	}
	
	public InputField enterText(String data) {
		findElement().sendKeys(data);
		return this;
	}

}
