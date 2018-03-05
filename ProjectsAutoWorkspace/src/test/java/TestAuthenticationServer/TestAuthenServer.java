package TestAuthenticationServer;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import AndroidScreen.AndroidHome;
import AndroidScreen.AndroidMessageDetail;
import AndroidScreen.AndroidMessages;
import AppiumLib.AppiumServer;
import CustomizeLibrary.AndroidUtility;
import CustomizeLibrary.SetUpPhoneProfile;
import CustomizeLibrary.ShellExecuter;
import Screens.LoginScreen;
import Screens.ValidationCodeScreen;
import Screens.WelcomeScreen;
import io.appium.java_client.android.AndroidDriver;

public class TestAuthenServer {
	
	AndroidDriver driver;
    AppiumServer appiumSrv;
    DesiredCapabilities cap;
    
    WelcomeScreen scrWelcome;
	LoginScreen scrLogin;
	WebDriverWait wait;
	ValidationCodeScreen scrValidation;
	AndroidHome scrAndroidHome;
	AndroidMessages scrAndroidMessage;
	AndroidMessageDetail scrAndroidMessageDetail;
	
	AndroidUtility androiUtility;
    
    SetUpPhoneProfile phoneProfileObj;
    String fileName = System.getProperty ("user.dir") + "/src/main/resources/DataFile.xlsx";
    final static Logger logger= Logger.getLogger(TestAuthenServer.class);
    
    @BeforeSuite
    public void starUpAppiumServer() throws Exception{
    	System.out.println("Start appium server.......");
    	appiumSrv= new AppiumServer();
        appiumSrv.startAppium();
        
    }
    
    @BeforeTest
	public void installApp() throws Exception{
		System.out.println("Install the app..............");        
        File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "/src/main/resources/App/");
		File app = new File(appDir, "KTAL.apk");
        
        System.out.println("Open app on device");
        phoneProfileObj = new SetUpPhoneProfile(fileName, "SetUpPhoneProfile");
        cap= phoneProfileObj.getDesiredCapabilitise();
        cap.setCapability("app",app.getAbsolutePath() );
        driver = new AndroidDriver(new URL("http://127.0.0.1:"+appiumSrv.getPortAppium()+"/wd/hub"), cap);
        Thread.sleep(1000*5);
        wait= new WebDriverWait(driver, 120);
	}
    
    
    @Test
	public void loginAccount() throws InterruptedException{
    	initScreens();
    	
		androiUtility=new AndroidUtility();
		
		// click skip button.
		scrWelcome.goToLoginScreen();
		
		// select the default number 
		scrLogin.loginToAccount();

		/*
		// back to the home screen
		androiUtility.backToHomeScreen();
		
		// select messages
		scrAndroidHome.goToInboxMessage();		
		
		// wait until there is an sms with name VERIFY send to phone.
		// click on sms VERIFY
		scrAndroidMessage.selectMessage("VERIFY");		
		
		
		// get the text
		// get the code
		String smsCode=scrAndroidMessageDetail.getValidationCode();
		
		// click recent app
		androiUtility.viewRecentApps();
		
		// select app with name "kTal"
		androiUtility.selectAppToView(driver, "kTal");
		
		// input validation code
		scrValidation.inputValidationCode(smsCode);
		
		// click validation button
		scrValidation.ValidateCode();
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Synchronisation")));
		
		
		// if validation code successfully it will go to Synchronisation screen.
		if(!driver.findElements(By.name("Synchronisation")).isEmpty()){
			Assert.assertTrue(true);
		}
		else{
			Assert.assertTrue(false);
		}
		*/

		scrLogin.allowKtalToAccessToReadSMS();

		scrLogin.allowKtalToSendAndViewSMS();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Synchronisation")));

		// if validation code successfully it will go to Synchronisation screen.
		if(!driver.findElements(By.name("Synchronisation")).isEmpty()){
			Assert.assertTrue(true);
		}
		else{
			Assert.assertTrue(false);
		}


	}
    
    
    
    @AfterTest
	public void unInstallAppAndDeleteOldSMS() throws InterruptedException{
    	//deleteOldSmsValidation();
		String appPackage=cap.getCapability("appPackage").toString();
		androiUtility.unInstallApp(appPackage);
	}
    
    
    @AfterSuite
	public void stopAppiumServer() throws Exception{		
		appiumSrv.stopAppium();
		System.out.println("testcase done");
	}
    
    
	
    public void initScreens(){
    	scrWelcome= new WelcomeScreen(driver);
		scrLogin= new LoginScreen(driver);
		scrAndroidHome= new AndroidHome(driver);
		wait= new WebDriverWait(driver, 120);
		scrValidation= new ValidationCodeScreen(driver);
		scrAndroidMessage=new AndroidMessages(driver);
		scrAndroidMessageDetail= new AndroidMessageDetail(driver);
    }

    /*
    public void deleteOldSmsValidation() throws InterruptedException{
		
    	// check recent apps
    	androiUtility.viewRecentApps();
		
		// select message 
    	androiUtility.selectAppToView(driver, "Messages");
    
    	// delete message
    	scrAndroidMessageDetail.deleteMessage();
    	
    	// view recent apps
    	androiUtility.viewRecentApps();
    	
    	
    	// close all apps
    	scrAndroidHome.closeAllApps();
	}
	*/
    

}
