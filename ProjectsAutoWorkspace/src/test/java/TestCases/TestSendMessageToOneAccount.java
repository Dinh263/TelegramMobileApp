package TestCases;

import AppiumLib.AppiumServer;

import CustomizeLibrary.StringGenerator;
import DataMapping.DataSendShortMessageToOneContact;
import CustomizeLibrary.SetUpPhoneProfile;
import Screens.ChattingScreen;
import Screens.ContactsSrceen;
import Screens.HomeScreen;
import Screens.SettingScreen;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator
 * This test case is used for testing the case of account A on device 01 send a short message to account B on device 02
 * Verify the account A send message successfully.
 * Verify the account B receive message successfully.
 * The step detail is :
 * step 1 : Start appium server. Function: "setUp"
 * step 2 : open app on device 01 (device send message). Function "openAppOnDeviceSendMessage".
 * step 3 : Initiate the instances of screens in device 01. Function "initInstanceOfScreens".
 * step 4 : Send a message to account B. Function "sendAShortMessageToAnAccount".
 * step 5 : Open app on device 02, cause account B exist on device 02. we need to re-initiate instances on device 02. Function "openAppOnDeviceReceivedMessage"
 * step 6 : Check if receive message successfully. Function "receiveMessageSuccessfully.
 * step 7 : stop appium server. Function "tearDown".
 */
public class TestSendMessageToOneAccount {

    AndroidDriver driver;
    AppiumServer appiumSrv;

    SetUpPhoneProfile phoneProfileSentObj;
    SetUpPhoneProfile phoneProfileReceiverObj;
    DataSendShortMessageToOneContact dataSend;

    String fileName = System.getProperty ("user.dir") + "/src/main/resources/DataFile.xlsx";
    String accountSentName = "";
    String accountReceivedName = "";
    String messageContent = "";


    HomeScreen scrHome;
    SettingScreen scrSetting;
    ContactsSrceen scrContacts;
    ChattingScreen scrChatting;

    final static Logger logger= Logger.getLogger(TestSendMessageToOneAccount.class);

    @BeforeSuite
    public void setUp() throws Exception {
        System.out.println("===start appium server====");
        appiumSrv= new AppiumServer();
        appiumSrv.startAppium();
    }

    @BeforeTest
    public void openAppOnDeviceSendMessage() throws IOException {
        System.out.println("Open app on device 01 to send message.");
        phoneProfileSentObj = new SetUpPhoneProfile(fileName, "SetUpPhoneProfileSend");
        System.out.println("Get profile of phone 01 (which sends message)");
        DesiredCapabilities cap= phoneProfileSentObj.getDesiredCapabilitise();
        driver = new AndroidDriver(new URL("http://127.0.0.1:"+appiumSrv.getPortAppium()+"/wd/hub"), cap);
    }

    @BeforeGroups(groups = "sendMessage")
    public void initScreensOnDeviceSendMessage(){
        initInstanceOfScreens();
    }

    @Test(priority = 1,groups = "sendMessage")
    public void sendAShortMessageToAnAccount() throws IOException {
        // click setting button to get the account Name.
        scrHome.goToSettingScreen();

        //Get Account Name
        accountSentName= scrSetting.getUserAccountName();
        System.out.println(accountSentName);

        //Go to Contacts List Screen
        scrSetting.goToContactScreen();

        dataSend = new DataSendShortMessageToOneContact(fileName);

        //Get the account name who receive message.
        //and get the content of message which will be sent.
        accountReceivedName = dataSend.getReceiverName();
        messageContent = StringGenerator.generateRandomString();

        // select account receive message.
        // send message.
        scrContacts.selectAccountNameToTest(accountReceivedName);
        scrChatting.sendMessage(messageContent);

        // wait until the message is sent.
        //String xpath = "//android.view.View[@content-desc='"+accountNameSend+":"+" "+messageContent+"']";
        scrChatting.waitUntilMessageSent(accountSentName,messageContent);

        //check if a message sent is shown on the screen.
        scrChatting.waitUntilMessageReceived(accountSentName,messageContent);
        Assert.assertTrue(scrChatting.isMessageSent(accountSentName,messageContent));

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        scrChatting.deleteMessage(accountSentName,messageContent);
        // clear driver of phone 1
        driver.quit();
    }


    @AfterGroups(groups = "sendMessage")
    public void openAppOnDeviceReceivedMessage() throws InterruptedException, IOException {
        Thread.sleep(5*1000);
        System.out.println("Get profile of phone which receive message");
        phoneProfileReceiverObj = new SetUpPhoneProfile(fileName,"SetUpPhoneProfileReceiver");
        DesiredCapabilities cap= phoneProfileReceiverObj.getDesiredCapabilitise();
        System.out.println("open app on device 02 , device receive message");
        driver = new AndroidDriver(new URL("http://127.0.0.1:"+appiumSrv.getPortAppium()+"/wd/hub"),cap);
        initInstanceOfScreens();
    }


    @Test(priority = 2,groups = "receiveMessage")
    public void receiveMessageSuccessfully(){
        // select a contact. This contact (on device 01)  is the person who send the message.
        scrHome.selectContact(accountSentName);

        // refresh chatting detail screen.
        scrChatting.refreshScreenChatting();
        scrChatting.waitUntilMessageReceived(accountSentName,messageContent);

        // check if receive message successfully.
        Assert.assertTrue(scrChatting.isMessageReceived(accountSentName,messageContent));

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        scrChatting.deleteMessage(accountSentName,messageContent);

        driver.quit();
    }



    @AfterSuite
    public void tearDown() throws Exception {
        System.out.println("===stop appium server=====");
        appiumSrv.stopAppium();
    }

    public void initInstanceOfScreens(){
        scrHome = new HomeScreen(driver);
        scrSetting = new SettingScreen(driver);
        scrContacts = new ContactsSrceen(driver);
        scrChatting = new ChattingScreen(driver);
    }
}
