package Screens;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginScreen {
	WebDriver driver;
	 WebDriverWait wait;
	 
	 @FindBy (id="com.bebound.zeegram:id/bt_login")
	 WebElement btnLogin;

	 @FindBy(name="OK")
	 WebElement btnOk;

	 @FindBy(name="Allow")
	 WebElement btnAllow;
	 
	 public LoginScreen(WebDriver driver){
		 this.driver=driver;
		 PageFactory.initElements(driver,this);
		 wait = new WebDriverWait(driver,120);
	 }
	 
	 
	 private void clickLoginButton(){
		 btnLogin.click();
	 }
	 
	 public void loginToAccount(){
		 clickLoginButton();
	 }

	 public void allowKtalToAccessToReadSMS(){
	 	btnOk.click();
	 }

	 public void allowKtalToSendAndViewSMS(){
	 	btnAllow.click();
	 }
	 
}
