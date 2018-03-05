package CustomizeLibrary;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;


public class SetUpPhoneProfile {
    ExcelUtility excelobj;

     public SetUpPhoneProfile(String fileName, String sheetName) throws IOException {
         excelobj = new ExcelUtility(fileName, sheetName);
     }

     public DesiredCapabilities getDesiredCapabilitise(){
         DesiredCapabilities desired = new DesiredCapabilities();
         desired.setCapability("deviceName", excelobj.getValueAt(1,1));
         desired.setCapability("browserName", excelobj.getValueAt(2,1));
         desired.setCapability("platformName", excelobj.getValueAt(3,1));
         desired.setCapability("platformVersion", excelobj.getValueAt(4,1));
         desired.setCapability("udid",excelobj.getValueAt(5,1));
         desired.setCapability("appPackage", excelobj.getValueAt(6,1));
         desired.setCapability("appActivity", excelobj.getValueAt(7,1));
         desired.setCapability("newCommandTimeout", excelobj.getValueAt(8,1));
         return desired;
     }
}
