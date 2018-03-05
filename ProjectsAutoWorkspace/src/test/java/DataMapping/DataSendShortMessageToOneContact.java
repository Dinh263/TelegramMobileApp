package DataMapping;

import java.io.IOException;

import CustomizeLibrary.ExcelUtility;

/**
 * Created by Administrator
 * this class is used to map data in the sheet "SendShortMessageToOneContact"
 */
public class DataSendShortMessageToOneContact {
    ExcelUtility excelobj;
    static final String SHEETNAME="SENDSHORTMESSAGETOONECONTACT";

    public DataSendShortMessageToOneContact(String fileName) throws IOException, IOException {
        excelobj = new ExcelUtility(fileName, SHEETNAME);
    }

    /**
     * This function is used for getting the account Name which receive the massage
     * (device 01 send message to device 02)   *
     * @return the account name (on device 02) which receive the massage.
     */
    public String getReceiverName(){
        return excelobj.getValueAt(1,1);
    }


    /**
     * This function is used for getting the content of message which is sent from device 01
     * (device 01 send message to device 02)   *
     * @return the content of message which is used for sending
     */
    public String getMessageContent(){
        return excelobj.getValueAt(2,1);
    }
}
