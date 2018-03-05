package Screens;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SettingScreen {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy (id="com.bebound.zeegram:id/lbl_name")
    WebElement usrAccountName;

    @FindBy (id="com.bebound.zeegram:id/material_drawer_name")
    WebElement menuContacts;

    public SettingScreen(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver,120);
    }

    /**
     * this function is used for getting the account name in setting screen
     * @return account name
     */
    public String getUserAccountName(){
       String userName = usrAccountName.getText();
        return userName;
    }

    /**
     * this function goes to contacts list
     */
     private void clickContactButton(){
        menuContacts.click();
    }

     /**
      * this function is used for launch Contacts screen
      */
    public void goToContactScreen(){
        clickContactButton();
    }
}
