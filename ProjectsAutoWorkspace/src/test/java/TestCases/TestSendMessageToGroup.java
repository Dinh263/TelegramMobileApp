package TestCases;

import AppiumLib.AppiumServer;
import CustomizeLibrary.StringGenerator;
import DataMapping.DataSendShortMessageToGroup;
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
 * This test case is used for testing the case of account A on device 01 send a short message to a group.
 * This group also exist on device 02.
 * Verify the account A on device 01 send message to group successfully.
 * Verify the group on device 02 receive message successfully.
 * The step detail is :
 * step 1 : Start appium server. Function "setUp".
 * step 2 : Open app on device 01 which is used for sending message to group.
 * step 3 : Initiae instances of screens of device 01. Function "initScreensOnDeviceSendMessage".
 * step 4 : Send short message to group. Function "sendShortMessageToGroup".
 * step 5 : Open app on device 02. Because this group also exist in device 02 . that is why we have to open app on device 02 to check receive message. Function "openAppOnDeviceReceivedMessage".
 * step 6 : Verify group receive message successfully. Function "receiveMessageSuccessfully".
 * step 7 : Stop appium server. Function "tearDown".
 */
public class TestSendMessageToGroup {
    AndroidDriver driver;
    AppiumServer appiumSrv;

    SetUpPhoneProfile phoneProfileSentObj;
    SetUpPhoneProfile phoneProfileReceiverObj;
    DataSendShortMessageToGroup dataSend;

    String fileName = System.getProperty ("user.dir") + "/src/main/resources/DataFile.xlsx";
    String accountSentName = "";
    String groupReceive = "";
    String messageContent = "";


    HomeScreen scrHome;
    SettingScreen scrSetting;
    ContactsSrceen scrContacts;
    ChattingScreen scrChatting;

    final static Logger logger= Logger.getLogger(TestSendMessageToGroup.class);


    /**
     * this function is just start up appium server
     * @throws Exception issue when start up appium server.
     */
    @BeforeSuite
    public void setUp() throws Exception {
        System.out.println("===start appium server====");
        appiumSrv= new AppiumServer();
        appiumSrv.startAppium();
    }


    /**
     * this function is used for open app on device 01.
     * Cause we use account A on device 01  to send message to group .
     * @throws IOException
     */
    @BeforeTest
    public void openAppOnDeviceSendMessage() throws IOException {
        System.out.println("Open app on device 01 to send message.");
        phoneProfileSentObj = new SetUpPhoneProfile(fileName, "SetUpPhoneProfileSend");
        System.out.println("Get profile of phone 01 (which sends message)");
        DesiredCapabilities cap= phoneProfileSentObj.getDesiredCapabilitise();
        driver = new AndroidDriver(new URL("http://127.0.0.1:"+appiumSrv.getPortAppium()+"/wd/hub"), cap);
    }


    /**
     * this function is used for initiating the instances of screen.
     */
    @BeforeGroups(groups = "sendMessageToGroup")
    public void initScreensOnDeviceSendMessage(){
        initInstanceOfScreens();
    }

    /**
     * this function is used for sending message to group
     * @throws IOException
     */
    @Test(priority = 1,groups = "sendMessageToGroup")
    public void sendShortMessageToGroup() throws IOException {
        // click setting button to get the account Name.
        scrHome.goToSettingScreen();

        //Get Account Name
        accountSentName= scrSetting.getUserAccountName();
        System.out.println(accountSentName);

        //Go to Contacts List Screen
        scrSetting.goToContactScreen();

        //Go to Home Screen chat
        scrContacts.goBackToHomeScreen();



        dataSend = new DataSendShortMessageToGroup(fileName);

        //Get the group name who receive message.
        //and get the content of message which will be sent.
        groupReceive = dataSend.getGroupName();
        messageContent = StringGenerator.generateRandomString();

        // select account receive message.
        // send message.
        scrHome.selectContact(groupReceive);
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


    /**
     * this function is used for open app on device 02.
     * Because device 02 has account which exists in the group.
     * @throws InterruptedException
     * @throws IOException
     */

    @AfterGroups(groups = "sendMessageToGroup")
    public void openAppOnDeviceReceivedMessage() throws InterruptedException, IOException {
        Thread.sleep(5*1000);
        System.out.println("Get proflie of phone which receive message");
        phoneProfileReceiverObj = new SetUpPhoneProfile(fileName,"SetUpPhoneProfileReceiver");
        DesiredCapabilities cap= phoneProfileReceiverObj.getDesiredCapabilitise();
        System.out.println("open app on device 02 , device receive message");
        driver = new AndroidDriver(new URL("http://127.0.0.1:"+appiumSrv.getPortAppium()+"/wd/hub"),cap);
        initInstanceOfScreens();
    }


    /**
     * this function is used for checking if group receive message successfully.
     */
    @Test(priority = 2,groups = "receiveMessage")
    public void receiveMessageSuccessfully(){
        scrHome.selectContact(groupReceive);
        scrChatting.refreshScreenChatting();
        scrChatting.waitUntilMessageReceived(accountSentName,messageContent);
        Assert.assertTrue(scrChatting.isMessageReceived(accountSentName,messageContent));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        scrChatting.deleteMessage(accountSentName,messageContent);
        driver.quit();
    }


    /**
     * this function is used for stop appium server.
     * @throws Exception
     */
    @AfterSuite
    public void tearDown() throws Exception {
        System.out.println("===stop appium server=====");
        appiumSrv.stopAppium();
    }


    /**
     * this function is used for initiating instances of screens.
     */
    public void initInstanceOfScreens(){
        scrHome = new HomeScreen(driver);
        scrSetting = new SettingScreen(driver);
        scrContacts = new ContactsSrceen(driver);
        scrChatting = new ChattingScreen(driver);
    }






}
