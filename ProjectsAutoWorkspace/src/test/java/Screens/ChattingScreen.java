package Screens;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ChattingScreen {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy (id = "com.bebound.zeegram:id/edit_message")
    WebElement txtMessage;

    @FindBy (id = "com.bebound.zeegram:id/bt_send")
    WebElement btnSendMessage;

    @FindBy (id="com.bebound.zeegram:id/action_refresh")
    WebElement btnRefresh;

    @FindBy(id="com.bebound.zeegram:id/action_reply")
    WebElement btnReply;

    @FindBy(id="com.bebound.zeegram:id/action_forward")
    WebElement btnForward;

    @FindBy(xpath="//android.widget.LinearLayout[@index='0']/android.widget.FrameLayout[@index='0']/android.widget.FrameLayout[@index='0']/android.view.ViewGroup[@index='1']/android.support.v7.widget.LinearLayoutCompat[@index='2']/android.widget.ImageView[@index='3']")
    WebElement btnMoreOption;

    @FindBy(name="Delete")
    WebElement btnDelete;

    @FindBy(name="YES")
    WebElement btnYesToDeleteMessage;

    @FindBy(xpath="//android.widget.ImageButton[@content-desc='Navigate up']")
    WebElement btnNavigateUp;

    public ChattingScreen (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver,120);
    }

    /**
     * this function is used for setting text (compose text , message)
     * @param message
     */
    private void setMessageText(String message){
        txtMessage.sendKeys(message);
    }
    
    /**
     * this function is used for compose message and click send button.
     * @param pMessage
     */
    public void sendMessage(String pMessage){
        setMessageText(pMessage);
        btnSendMessage.click();
    }

    /**
     * this function is used for checking if a message is sent or not.
     * @param pAccountName the account name of account who send message.
     * @param pMessageContent the content of message which is sent.
     * @return true if message is sent.
     */
    public boolean isMessageSent(String pAccountName, String pMessageContent){
        String xpath = "//android.view.View[@content-desc='"+pAccountName+":"+" "+pMessageContent+"']";
        if(!driver.findElements(By.xpath(xpath)).isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * this function is used for checking if a message is replied or not.
     * @param pAccountName who reply the message
     * @param pMessageContent the content of message 
     * @return true if message is replied.
     */
    public boolean isMessageReplied(String pAccountName, String pMessageContent){
    	return isMessageSent(pAccountName, pMessageContent);
    }

    /**
     * this function is used for checking if a message is received or not.
     * @param pAccountName the account name of account who send message.
     * @param pMessageContent the content of message which is received.
     * @return true if message is received.
     */
    public boolean isMessageReceived(String pAccountName,String pMessageContent){
        String xpath = "//android.view.View[@content-desc='"+pAccountName+":"+" "+pMessageContent+"']";
        if(!driver.findElements(By.xpath(xpath)).isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
    public void waitUntilMessageSent(String pAccountName, String pMessageContent){
        String xpath = "//android.view.View[@content-desc='"+pAccountName+":"+" "+pMessageContent+"']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public void waitUntilMessageReceived(String pAccountName, String pMessageContent){
        String xpath = "//android.view.View[@content-desc='"+pAccountName+":"+" "+pMessageContent+"']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    /**
     * this function is used for clicking refresh button at the chatting screen.
     */
    private void clickRefreshButton(){
        btnRefresh.click();
    }


    /**
     * this function is used for refreshing screen at the screen chatting.
     */
    public void refreshScreenChatting(){
        clickRefreshButton();
    }


    /**
     * this function is used for reply on the message
     * @param oldMessage : the message which is received
     * @param fromPerson : who sent the message
     * @param replyMessage : new message which is usded for reply
     */
    public void replyOnMessage(String oldMessage, String fromPerson, String replyMessage){
        TouchAction action=new TouchAction((AndroidDriver)driver);
        String xpath = "//android.view.View[@content-desc='"+fromPerson+":"+" "+oldMessage+"']";
        action.longPress(driver.findElement(By.xpath(xpath))).perform();
        btnReply.click();
        setMessageText(replyMessage);
        btnSendMessage.click();
    }

    
    /**
     * this function is used for selecting a message which will be forwarded to other account.
     * @param oldMessage the original message
     * @param fromPerson the person who sent message
     */
    public void selectMessageToForward(String oldMessage, String fromPerson){
        TouchAction action= new TouchAction((AndroidDriver)driver);
        String xpath="//android.view.View[@content-desc='"+fromPerson+":"+" "+oldMessage+"']";
        action.longPress(driver.findElement(By.xpath(xpath))).perform();
        btnForward.click();
    }

    /**
     * this function is used for forwarding message.
     * @param forwardMessage additional message when forwarding.
     */
    public void forwardMessage(String forwardMessage){
        if(forwardMessage.length()==0){
            // in case, user don't want to input any more before forward message.
            btnSendMessage.click();
        }
        else{
        	// in case, user they want to input some more message with forwarded message.
            setMessageText(forwardMessage);
            btnSendMessage.click();
        }
    }

    /**
     * this function is check if a message is forward succefully.
     * @param pAccountName who forward the message
     * @param pMessageContent the message which is forwarded.
     * @return true if message is forward succefully and false if other.
     */
    public boolean isMessageForwardSuccessfully(String pAccountName, String pMessageContent){
        return isMessageSent(pAccountName,pMessageContent);
    }

    public void deleteMessage(String pAccountName, String pMessageContent){
        String xpath="//android.view.View[@content-desc='"+pAccountName+":"+" "+pMessageContent+"']";
        TouchAction action= new TouchAction((AndroidDriver)driver);
        action.longPress(driver.findElement(By.xpath(xpath))).perform();
        wait.until(ExpectedConditions.visibilityOf(btnMoreOption));
        btnMoreOption.click();
        btnDelete.click();
        btnYesToDeleteMessage.click();
    }

    public void goBackPreviousScreen(){
        btnNavigateUp.click();
    }
}
