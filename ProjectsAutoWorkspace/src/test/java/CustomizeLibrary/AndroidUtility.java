package CustomizeLibrary;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.appium.java_client.android.AndroidDriver;

public class AndroidUtility {
	ShellExecuter shellObj;

	
	public AndroidUtility(){
		shellObj= new ShellExecuter();
		
	}
	
	/**
	 * this function is used for back the home screen.
	 */
	public void backToHomeScreen(){
		shellObj.ExecuterCommand("adb shell input keyevent KEYCODE_HOME");
	}
	
	/**
	 * this function is used for view recent apps.
	 */
	public void viewRecentApps(){
		shellObj.ExecuterCommand("adb shell input keyevent KEYCODE_APP_SWITCH");
	}
	
	/**
	 * this function is used for select an app to view in the recent view.
	 * @param driver android driver
	 * @param appName the name of app which you want to see
	 * @throws InterruptedException
	 */
	public void selectAppToView(AndroidDriver driver, String appName) throws InterruptedException{
		driver.findElement(By.name(appName)).click();
		Thread.sleep(3*1000);
	}
	
	public void unInstallApp(String appPackage){
		shellObj.ExecuterCommand("adb uninstall "+appPackage);
	}

}
