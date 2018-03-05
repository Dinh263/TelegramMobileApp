package TestCases;

import AppiumLib.AppiumServer;
import CustomizeLibrary.StringGenerator;
import DataMapping.DataReplyShortMessageToOneContact;
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
 * this test case is used for checking if an account reply a message successfully or not.
 * Pre-codition : account A on device 01 send a message to account B on device 02.
 * Then account B will reply a message to account A.
 * Verify account B reply message successfully.
 * Verify account A receive the replied message successfully.
 * The step detail is:
 * Precondition step 1 : start appium server. Function "setUp".
 * Precondition step 2 : open app on device 01. then send a message to account B. Function "device01SendMessageToDevice02".
 * step 3 : open app on device 02. Reply on the message which is sent from step 2. Function "replyAShortMessageToAnAccount".
 * step 4 : open app on device 01.  Function "openAppOnDevice01ToCheckReceiveReplyMessage"
 * step 5 : if account A receive replyed message successfully or not. Function "checkIfReceiveMessageReplySuccessfully"
 * step 6 : stop appium server.
 */


public class TestReplyMessageToOneAccount {
    AndroidDriver driver;
    AppiumServer appiumSrv;

    SetUpPhoneProfile phoneProfileSentObj;
    SetUpPhoneProfile phoneProfileReceiverObj;
    DataReplyShortMessageToOneContact dataSendReply;

    String fileName = System.getProperty ("user.dir") + "/src/main/resources/DataFile.xlsx";
    String accountSentName = "";
    String accountReceivedName = "";
    String messageSentContent = "";
    String messageRepliedContent = "";


    HomeScreen scrHome;
    SettingScreen scrSetting;
    ContactsSrceen scrContacts;
    ChattingScreen scrChatting;


    final static Logger logger= Logger.getLogger(TestReplyMessageToOneAccount.class);


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
     * this function is used for sending message to an account B on device 02.
     * @throws IOException
     * @throws InterruptedException
     */
    @BeforeTest
    public void device01SendMessageToDevice02() throws IOException, InterruptedException {
        // open app on device 01 which send message
        openAppOnDevice01();

        // init screens objects
        initInstanceOfScreens();

        // device 01 send a short message to device 02
        sendMessage();

        // open app on device 02
        openAppOnDevice02();


    }


    /**
     * this function is used for replying a message to account on device 01 which is sent message before.
     */
    @Test (priority = 1,groups = "replyMessage")
    public void replyAShortMessageToAnAccount(){

        // select contact who sent message
        scrHome.selectContact(accountSentName);

        // refresh screen
        scrChatting.refreshScreenChatting();

        // wait until device 02 receive message from device 01
        scrChatting.waitUntilMessageReceived(accountSentName,messageSentContent);

        messageRepliedContent=StringGenerator.generateRandomString();
        scrChatting.replyOnMessage(messageSentContent,accountSentName,messageRepliedContent);

        accountReceivedName=dataSendReply.getAccountReceive();
        scrChatting.waitUntilMessageSent(accountReceivedName,messageRepliedContent);
        Assert.assertTrue(scrChatting.isMessageReplied(accountReceivedName, messageRepliedContent));

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        scrChatting.deleteMessage(accountReceivedName,messageRepliedContent);
        scrChatting.deleteMessage(accountSentName,messageSentContent);

        driver.quit();
    }

    /**
     * this function is used for opening app on device 01
     * @throws IOException
     */
    @AfterGroups(groups ="replyMessage" )
    public void openAppOnDevice01ToCheckReceiveReplyMessage() throws IOException {
        // open app on device 01 again to check if receive the reply message
        openAppOnDevice01();

        // init the instance of screen on device 01
        initInstanceOfScreens();
    }


    /**
     * this function is used for checking if account A on device 01 receive reply message from account B on device 02
     */
    @Test(priority = 2, groups = "checkReplyMessage")
    public void checkIfReceiveMessageReplySuccessfully(){

        // select account which reply the message.
        scrHome.selectContact(accountReceivedName);

        // refresh chatting screen
        scrChatting.refreshScreenChatting();

        scrChatting.waitUntilMessageReceived(accountReceivedName,messageRepliedContent);

        Assert.assertTrue(scrChatting.isMessageReceived(accountReceivedName,messageRepliedContent));

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        scrChatting.deleteMessage(accountReceivedName,messageRepliedContent);

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
     * this function is used for opening app on device 01
     * @throws IOException
     */
    public void openAppOnDevice01() throws IOException {
        System.out.println("Open app on device 01");
        phoneProfileSentObj = new SetUpPhoneProfile(fileName, "SetUpPhoneProfileSend");
        System.out.println("Get profile of phone 01 ");
        DesiredCapabilities cap= phoneProfileSentObj.getDesiredCapabilitise();
        driver = new AndroidDriver(new URL("http://127.0.0.1:"+appiumSrv.getPortAppium()+"/wd/hub"), cap);
    }


    /**
     * this function is used for opeing app on device 02
     * @throws InterruptedException
     * @throws IOException
     */
    public void openAppOnDevice02() throws InterruptedException, IOException {
        Thread.sleep(5*1000);
        System.out.println("Get proflie of phone which receive message (device 02) ");
        phoneProfileReceiverObj = new SetUpPhoneProfile(fileName,"SetUpPhoneProfileReceiver");
        DesiredCapabilities cap= phoneProfileReceiverObj.getDesiredCapabilitise();
        System.out.println("open app on device 02 , device receive message");
        driver = new AndroidDriver(new URL("http://127.0.0.1:"+appiumSrv.getPortAppium()+"/wd/hub"),cap);
        initInstanceOfScreens();
    }


    /**
     * this function is used for sending message.
     * @throws IOException
     */
    public void sendMessage() throws IOException {
        // click setting button to get the account Name.
        scrHome.goToSettingScreen();

        //Get Account Name
        accountSentName= scrSetting.getUserAccountName();
        System.out.println(accountSentName);

        //Go to Contacts List Screen
        scrSetting.goToContactScreen();

        dataSendReply= new DataReplyShortMessageToOneContact(fileName);

        //Get the account name who receive message.
        //and get the content of message which will be sent.
        accountReceivedName = dataSendReply.getAccountReceive();
        messageSentContent = StringGenerator.generateRandomString();

        // select account receive message.
        // send message.
        scrContacts.selectAccountNameToTest(accountReceivedName);
        scrChatting.sendMessage(messageSentContent);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        scrChatting.deleteMessage(accountSentName,messageSentContent);
        // clear driver of phone 1
        driver.quit();

    }


    /**
     * this function is used for initiating isntances of screen.
     */
    public void initInstanceOfScreens(){
        scrHome = new HomeScreen(driver);
        scrSetting = new SettingScreen(driver);
        scrContacts = new ContactsSrceen(driver);
        scrChatting = new ChattingScreen(driver);
    }


}
