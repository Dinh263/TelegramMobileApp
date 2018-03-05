package AndroidScreen;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AndroidHome {
	
	WebDriver driver;
	WebDriverWait wait;
	
	
	@FindBy(name="Messages")
	WebElement lbelMessage;
	
	@FindBy(id="com.android.systemui:id/recents_removeall_button")
	WebElement btnCloseAll;
	
	
	public AndroidHome(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
		wait= new WebDriverWait(driver, 120);
	}
	
	
	/**
	 * this function is used to click the messages at the home screen of android
	 */
	private void clickMessages(){
		wait.until(ExpectedConditions.visibilityOf(lbelMessage));
		lbelMessage.click();
	}
	
	
	/**
	 * this function is used to click the messages at the home screen of android
	 */
	public void goToInboxMessage(){
		clickMessages();
	}
	
	public void closeAllApps(){
		wait.until(ExpectedConditions.visibilityOf(btnCloseAll));
		btnCloseAll.click();
	}

}
