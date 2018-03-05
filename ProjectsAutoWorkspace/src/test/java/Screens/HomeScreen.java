package Screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomeScreen {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy (xpath = "//android.widget.FrameLayout[@index='0']/android.support.v4.widget.DrawerLayout[@index='0']/android.widget.FrameLayout[@index='0']/android.view.ViewGroup[@index='0']/android.widget.ImageButton[@index='0']")
    WebElement btnHome;



    public HomeScreen(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver,120);
    }


    /**
     * this function is used for clicking on the Home button.
     */
    private void clickHomeButton(){
        btnHome.click();
    }

    /**
     * this function is used for launch Setting screen.
     */
    public void goToSettingScreen(){
        clickHomeButton();
    }

    /**
     * this function is used for clicking on a contact 
     * @param contactName
     */
    public void selectContact(String contactName){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(contactName)));
        WebElement elContact = driver.findElement(By.name(contactName));
        elContact.click();
    }

    /**
     * this function is used for clicking on a group 
     * @param contactName
     */
    public void selectGroup(String groupName){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(groupName)));
        WebElement elContact = driver.findElement(By.name(groupName));
        elContact.click();
    }



}
