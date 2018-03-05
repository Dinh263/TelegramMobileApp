package AndroidScreen;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AndroidMessageDetail {
	
	WebDriver driver;
	WebDriverWait wait;
	
	@FindBy(id="com.android.mms:id/list_item_text_view")
	WebElement smsContent;
	
	@FindBy(name="Delete")
	WebElement btnDelete;
	
	@FindBy(id="com.android.mms:id/select_all_checkbox")
	WebElement chkAll;
	
	public AndroidMessageDetail(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
		wait= new WebDriverWait(driver, 120);
	}
	
	/**
	 * this function is used for getting the sms validation code sent by server.
	 * Get the content of sms validation. Then split it into array.
	 * The code will be the thrid word count from the beginning. 
	 * @return the validation code.
	 */
	public String getValidationCode(){
		wait.until(ExpectedConditions.visibilityOf(smsContent));
		String messageContent=smsContent.getText();
		String []temp=messageContent.split(" ");
		String Code=temp[2];
		return Code;
	}
	
	
	public void deleteMessage(){
		wait.until(ExpectedConditions.visibilityOf(btnDelete));
		btnDelete.click();
		wait.until(ExpectedConditions.visibilityOf(chkAll));
		chkAll.click();
		wait.until(ExpectedConditions.visibilityOf(btnDelete));
		btnDelete.click();
		wait.until(ExpectedConditions.visibilityOf(btnDelete));
		btnDelete.click();
	}
	
	
}
