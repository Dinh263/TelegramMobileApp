package AndroidScreen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AndroidMessages {
	WebDriver driver;
	WebDriverWait wait;
	
	
	
	
	public AndroidMessages(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
		wait= new WebDriverWait(driver, 120);
	}
	
	public void selectMessage(String fromPerson){
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(fromPerson)));
		WebElement messageFrom=driver.findElement(By.name(fromPerson));
		messageFrom.click();
	}
	
	
	
	
}
