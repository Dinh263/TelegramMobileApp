package AppiumLib;
import CustomizeLibrary.ExcelUtility;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import java.io.File;
import java.io.IOException;


public class AppiumServer {

    String Appium_Node_Path="";
    String Appium_JS_Path="";
    int portAppium;
    static AppiumDriverLocalService service;
    static String service_url;


    private void initValue() throws IOException {
        String fileName = System.getProperty ("user.dir") + "/src/main/resources/DataFile.xlsx";
        ExcelUtility excelobj= new ExcelUtility(fileName,"AppiumServerConfiguration");
        Appium_Node_Path=excelobj.getValueAt(1,1)+"\\node.exe";
        Appium_JS_Path=excelobj.getValueAt(1,1)+"\\node_modules\\appium\\bin\\appium.js";
        portAppium=Integer.parseInt(excelobj.getValueAt(2,1));
    }

    public  void startAppium() throws Exception{

        initValue();
        service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().
                usingPort(portAppium).usingDriverExecutable(new File(Appium_Node_Path)).
                withAppiumJS(new File(Appium_JS_Path)));
        service.start();
        Thread.sleep(25000);
        service_url = service.getUrl().toString();
    }

    public  void stopAppium() throws Exception{
        service.stop();
    }

    public int getPortAppium(){
        return portAppium;
    }
}
