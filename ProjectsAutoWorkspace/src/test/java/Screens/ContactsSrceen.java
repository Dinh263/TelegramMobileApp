package Screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class ContactsSrceen {
    WebDriver driver;

    @FindBy(xpath="//android.widget.ImageButton[@content-desc='Navigate up']")
    WebElement btnNavigateUp;

    public ContactsSrceen(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * this function is used for getting the account Name 
     * @param paccountName
     */
    public void selectAccountNameToTest(String paccountName){
        driver.findElement(By.name(paccountName)).click();
    }

    /**
     * this function is used for clicking on the button navigate back
     */
    private void clickButtonNavigateUp(){
        btnNavigateUp.click();
    }
    
    /**
     * this function is used for go back to Home screen.
     */
    public void goBackToHomeScreen(){
        clickButtonNavigateUp();
    }
}
