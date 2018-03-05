package Screens;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class
ValidationCodeScreen {
	 WebDriver driver;
	 WebDriverWait wait;
	 
	 @FindBy (id="com.bebound.zeegram:id/edit_code")
	 WebElement txtCode;
	 
	 @FindBy (id="com.bebound.zeegram:id/lbl_send")
	 WebElement btnValidate;
	 
	 public ValidationCodeScreen(WebDriver driver){
		 this.driver=driver;
		 PageFactory.initElements(driver, this);
		 wait = new WebDriverWait(driver,120);
		  }
	 
	 public void inputValidationCode(String smsCode){
		 txtCode.sendKeys(smsCode);
	 }
	 
	 private void clickValidateButton(){
		 btnValidate.click();
	 }
	 
	 public  void ValidateCode(){
		 clickValidateButton();
	 }
	 

}
