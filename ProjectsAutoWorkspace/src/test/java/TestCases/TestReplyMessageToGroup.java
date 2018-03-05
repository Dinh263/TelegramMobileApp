package TestCases;

import AppiumLib.AppiumServer;
import CustomizeLibrary.StringGenerator;
import DataMapping.DataReplyShortMessageToGroup;
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
 * this test case is used for checking if an account reply a message to a group and group receive the message successfully or not.
 * Pre-codition : account A on device 01 send a message to a group
 * Then account B which exists on the group , on device 02 reply a message to that group.
 * Verify account B reply message to group successfully.
 * Verify group receive the replied message successfully.
 * The step detail is:
 * Precondition step 1 : start appium server. Function "setUp".
 * Precondition step 2 : + This function is used for opening  app on device 01.
 *                       + initiate instances of screens
 *                       + select a group and send e massage to group.
 *                       + open app on device 02
 *                       + re-initiate instances of screen on device 02.
 *                       Function "device01SendMessageToDevice02".
 * Step 3 : account B on device 02 reply a message to group. Verify account B reply message successfully. Function "replyAShortMessageToGroup"
 * Step 4 : open app on device 01 to check group receive message or not. Function "openAppOnDevice01ToCheckReceiveReplyMessage".
 * Step 5 : check if group receive reply message from account B successfully. Function "checkIfReceiveMessageReplySuccessfully".
 */


public class TestReplyMessageToGroup {
    AndroidDriver driver;
    AppiumServer appiumSrv;

    SetUpPhoneProfile phoneProfileSentObj;
    SetUpPhoneProfile phoneProfileReceiverObj;
    DataReplyShortMessageToGroup dataSendReply;

    String fileName = System.getProperty ("user.dir") + "/src/main/resources/DataFile.xlsx";
    String accountSentName = "";
    String accountReceivedName="";
    String groupName = "";
    String messageSentContent = "";
    String messageRepliedContent = "";


    HomeScreen scrHome;
    SettingScreen scrSetting;
    ContactsSrceen scrContacts;
    ChattingScreen scrChatting;


    final static Logger logger= Logger.getLogger(TestReplyMessageToGroup.class);

    /**
     * this function is used for starting appium server.
     * @throws Exception
     */
    @BeforeSuite
    public void setUp() throws Exception {
        System.out.println("===start appium server====");
        appiumSrv= new AppiumServer();
        appiumSrv.startAppium();
    }


    /**
     * this function is used for open app on device 01.
     * initiate instances of screens
     * select a group and send e massage to group.
     * open app on device 02
     * re-initiate instances of screen on device 02.
     * @throws IOException
     * @throws InterruptedException
     */
    @BeforeTest
    public void device01SendMessageToDevice02() throws IOException, InterruptedException {
        // open app on device 01 which send message
        openAppOnDevice01();

        // init screens objects
        initInstanceOfScreens();

        // device 01 send a short message to group
        sendMessageToGroup();

        // open app on device 02
        openAppOnDevice02();

        // re-init screens
        initInstanceOfScreens();
    }


    /**
     * this function is used for replying a message to a group
     */
    @Test (priority = 1,groups = "replyMessageToGroup")
    public void replyAShortMessageToGroup(){


        // select contact who sent message
        scrHome.selectGroup(groupName);

        // refresh screen
        scrChatting.refreshScreenChatting();


        // wait until device 02 receive message from device 01
        scrChatting.waitUntilMessageReceived(accountSentName,messageSentContent);

        scrChatting.replyOnMessage(messageSentContent,accountSentName,messageRepliedContent);


        scrChatting.waitUntilMessageSent(accountReceivedName,messageRepliedContent);
        Assert.assertTrue(scrChatting.isMessageReplied(accountReceivedName, messageRepliedContent));

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        scrChatting.deleteMessage(accountSentName,messageSentContent);

        scrChatting.deleteMessage(accountReceivedName,messageRepliedContent);

        driver.quit();

    }


    @AfterGroups(groups ="replyMessageToGroup" )
    public void openAppOnDevice01ToCheckReceiveReplyMessage() throws IOException {
        // open app on device 01 again to check if group receive message successfully
        openAppOnDevice01();

        // init the instance of screen on device 01
        initInstanceOfScreens();
    }

    /**
     * this function is used to check if a group receive reply message successfully.
     */
    @Test(priority = 2, groups = "checkReplyMessage")
    public void checkIfReceiveMessageReplySuccessfully(){

        // select group  which receive the message.
        scrHome.selectGroup(groupName);

        // refresh chatting screen
        scrChatting.refreshScreenChatting();

        //check if group receive message from device 02
        scrChatting.waitUntilMessageReceived(accountReceivedName,messageRepliedContent);

        Assert.assertTrue(scrChatting.isMessageReceived(accountReceivedName,messageRepliedContent));

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        scrChatting.deleteMessage(accountReceivedName,messageRepliedContent);

        driver.quit();
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
     * this function is used for initiating instances of screens.
     */
    public void initInstanceOfScreens(){
        scrHome = new HomeScreen(driver);
        scrSetting = new SettingScreen(driver);
        scrContacts = new ContactsSrceen(driver);
        scrChatting = new ChattingScreen(driver);
    }


    /**
     * this function is used for sending message to group
     * @throws IOException
     */
    public void sendMessageToGroup() throws IOException {
        // click setting button to get the account Name.
        scrHome.goToSettingScreen();

        //Get Account Name
        accountSentName= scrSetting.getUserAccountName();
        System.out.println(accountSentName);

        //Go to Contacts List Screen
        scrSetting.goToContactScreen();

        dataSendReply= new DataReplyShortMessageToGroup(fileName);

        //Get the account name who receive message.
        //and get the content of message which will be sent.
        accountReceivedName = dataSendReply.getAccountReceive();
        groupName=dataSendReply.getGroupName();
        messageSentContent = StringGenerator.generateRandomString();
        messageRepliedContent=StringGenerator.generateRandomString();

        // select group to send message.
        // send message.
        scrContacts.goBackToHomeScreen();
        scrHome.selectGroup(groupName);
        scrChatting.sendMessage(messageSentContent);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        scrChatting.deleteMessage(accountSentName,messageSentContent);

        // clear driver of phone 1
        driver.quit();

    }

    /**
     * this function is used for opening app on device 02
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
    }

    /**
     * this function is used for stopping appium server
     * @throws Exception
     */
    @AfterSuite
    public void tearDown() throws Exception {
        System.out.println("===stop appium server=====");
        appiumSrv.stopAppium();
    }


}
