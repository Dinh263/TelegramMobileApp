package Screens;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WelcomeScreen {
	 WebDriver driver;
	 WebDriverWait wait;
	 
	 @FindBy (id="com.bebound.zeegram:id/bt_skip")
	 WebElement btnSkip;
	 
	 public WelcomeScreen(WebDriver driver){
		 this.driver=driver;
		 PageFactory.initElements(driver,this);
		 wait = new WebDriverWait(driver,120);
	 }
	 
	 
	 private void clickSkipButton(){
		 btnSkip.click();
	 }
	 
	 public void goToLoginScreen(){
		 clickSkipButton();
	 }
	 
	 
}
