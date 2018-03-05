package TestCases;

import AppiumLib.AppiumServer;
import CustomizeLibrary.StringGenerator;
import DataMapping.DataForwardShortMessageToGroup;
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
 * this test case is used for checking if an account forward a message to a group and group receive the message successfully or not.
 * Pre-codition : account A on device 01 send a message to a account B (device 02)
 * Then account B will forward a massage to group  (on device 01)
 * Verify account B forward message to group successfully.
 * Verify group receive forwarded message successfully.
 * The step detail is:
 * Precondition step 1 : start appium server. Function "setUp".
 * Precondition step 2 : + This function is used for opening  app on device 01.
 *                       + initiate instances of screens
 *                       + select a contact (Account B) and send e massage to account B.
 *                       + open app on device 02
 *                       + re-initiate instances of screen on device 02.
 *                       Function "device01SendMessageToDevice02".
 * Step 3 : account B on device 02 forward a message to  group on device 01. Verify account B forward message successfully. Function "forwardShortMessageToAnGroup"
 * Step 4 : open app on device 01 to check group  receive message or not. Function "openAppOnDevice01ToCheckIfGroupReceiveForwardMessage".
 * Step 5 : check if group receive forward message from account B successfully. Function "checkIfGroupReceiveForwardMessageSuccessfully".
 */

public class TestForwardMessageToGroup {
    AndroidDriver driver;
    AppiumServer appiumSrv;

    SetUpPhoneProfile phoneProfileSentObj;
    SetUpPhoneProfile phoneProfileReceiverObj;
    DataForwardShortMessageToGroup dataSendForward;

    String fileName = System.getProperty ("user.dir") + "/src/main/resources/DataFile.xlsx";
    String accountSentName = "";
    String groupReceiveForwardName="";
    String accountForwardName = "";
    String messageSentContent = "";
    String messageForwardContent = "";


    HomeScreen scrHome;
    SettingScreen scrSetting;
    ContactsSrceen scrContacts;
    ChattingScreen scrChatting;


    final static Logger logger= Logger.getLogger(TestForwardMessageToGroup.class);


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
     * select a contact (account B) and send e massage to contact.
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

        // device 01 send a short message to device 02
        sendMessage();

        // open app on device 02 to forward the message to device 01.
        openAppOnDevice02();

        // re-init screen
        initInstanceOfScreens();


    }

    /**
     * this function is used for forwarding a massage from account B (on device 02) to a group (on device 01).
     */
    @Test(priority = 1,groups="forwardMessageToGroup")
    public void forwardShortMessageToAnGroup(){

        // select account name which send the message
        scrHome.selectContact(accountSentName);


        // refresh man hinh chating
        scrChatting.refreshScreenChatting();

        // wait until receive the message from device 01
        scrChatting.waitUntilMessageReceived(accountSentName,messageSentContent);

        //select message to forward
        scrChatting.selectMessageToForward(messageSentContent,accountSentName);

        // select device 01 to forward message
        groupReceiveForwardName=dataSendForward.getGroupNameToReceiveForwardMessage();
        scrHome.selectGroup(groupReceiveForwardName);

        messageForwardContent= StringGenerator.generateRandomString();

        scrChatting.forwardMessage(messageForwardContent);

        // check if forward message successfully.
        // there are 2 case
        // case 1 : account only forward the original message
        // case 2 : beside original message, account also add new message.
        // so in case 1 : we check the original message is shown on the screen is enough.
        // in case 2 : we have to check both original message and new message must be shown on the screen.



        // checking case 1
        if(messageForwardContent.length()==0){
            Assert.assertTrue(scrChatting.isMessageForwardSuccessfully(accountForwardName,messageSentContent));
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            scrChatting.deleteMessage(accountForwardName,messageSentContent);
        }
        else{
            // checking for case 2
            Assert.assertTrue(scrChatting.isMessageForwardSuccessfully(accountForwardName,messageSentContent));
            Assert.assertTrue(scrChatting.isMessageForwardSuccessfully(accountForwardName,messageForwardContent));
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            scrChatting.deleteMessage(accountForwardName,messageSentContent);
            scrChatting.deleteMessage(accountForwardName,messageForwardContent);
        }

        scrChatting.goBackPreviousScreen();
        scrHome.selectContact(accountSentName);
        scrChatting.deleteMessage(accountSentName,messageSentContent);


        driver.quit();

    }


    /**
     * this function is used for open app on device 01 to check if group receive message
     * @throws IOException
     */
    @AfterGroups(groups ="forwardMessageToGroup" )
    public void openAppOnDevice01ToCheckIfGroupReceiveForwardMessage() throws IOException {
        // open app on device 01 again to check if group receive message successfully
        openAppOnDevice01();

        // init the instance of screen on device 01
        initInstanceOfScreens();
    }

    /**
     * this function is used for checking if group receive message successfully.
     */
    @Test(priority = 2)
    public void checkIfGroupReceiveForwardMessageSuccessfully(){
        // select account which receive forward message
        scrHome.selectGroup(groupReceiveForwardName);

        // refresh chatting screen
        scrChatting.refreshScreenChatting();

        //check if group receive foraward message from device 02
        // there are 2 cases for checking
        // case 1 : in case account only forward the original message to other
        // case 2 : beside original message, account also send new message.

        if(messageForwardContent.length()==0){
            scrChatting.waitUntilMessageReceived(accountForwardName,messageSentContent);
            Assert.assertTrue(scrChatting.isMessageForwardSuccessfully(accountForwardName,messageSentContent));
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            scrChatting.deleteMessage(accountForwardName,messageSentContent);
        }
        else{
            scrChatting.waitUntilMessageReceived(accountForwardName,messageSentContent);
            scrChatting.waitUntilMessageReceived(accountForwardName,messageForwardContent);
            Assert.assertTrue(scrChatting.isMessageForwardSuccessfully(accountForwardName,messageSentContent));
            Assert.assertTrue(scrChatting.isMessageForwardSuccessfully(accountForwardName,messageForwardContent));
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            scrChatting.deleteMessage(accountForwardName,messageSentContent);
            scrChatting.deleteMessage(accountForwardName,messageForwardContent);
        }

        driver.quit();
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


    /**
     * this fuction is used for open app on device 01
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


    public void sendMessage() throws IOException {
        // click setting button to get the account Name.
        scrHome.goToSettingScreen();

        //Get Account Name
        accountSentName= scrSetting.getUserAccountName();
        System.out.println(accountSentName);

        //Go to Contacts List Screen
        scrSetting.goToContactScreen();

        dataSendForward= new DataForwardShortMessageToGroup(fileName);

        //Get the account name who receive message.
        //and get the content of message which will be sent.
        accountForwardName = dataSendForward.getAccountForwardMessage();
        messageSentContent = StringGenerator.generateRandomString();

        // select account receive message.
        // send message.
        scrContacts.selectAccountNameToTest(accountForwardName);
        scrChatting.sendMessage(messageSentContent);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        scrChatting.deleteMessage(accountSentName,messageSentContent);

        // clear driver of phone 1
        driver.quit();

    }

    /**
     * this fuction is used for open app on device 02
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




}
