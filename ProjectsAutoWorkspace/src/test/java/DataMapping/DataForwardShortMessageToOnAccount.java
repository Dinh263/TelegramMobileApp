package DataMapping;



import java.io.IOException;

import CustomizeLibrary.ExcelUtility;


/**
 * Created by Administrator
 * this class is used to mapped with the data in the sheet "ForwardShortMessageToOnAccount"
 */
public class DataForwardShortMessageToOnAccount {
    ExcelUtility excelobj;
    final static String SHEETNAME="FORWARDSHORTMESSAGETOONACCOUNT";
    public DataForwardShortMessageToOnAccount(String fileName) throws IOException {
        excelobj=new ExcelUtility(fileName, SHEETNAME);
    }

    /**
     * this function is used for getting account which forward message.
     * @return account name which forwards message.
     */
    public String getAccountForward(){
        return excelobj.getValueAt(1,1);
    }


    /**
     * this function is used for getting content of message send.
     * @return content of message sent.
     */
    public String getMessageContentSend(){
        return excelobj.getValueAt(2,1);
    }


    /**
     * this function is used for getting the massage which is used for forwarding.
     * @return the content of massage forwarded.
     */
    public String getMessageContentForward(){
        return excelobj.getValueAt(3,1);
    }
}
