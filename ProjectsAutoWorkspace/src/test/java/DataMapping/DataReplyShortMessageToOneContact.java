package DataMapping;

import java.io.IOException;

import CustomizeLibrary.ExcelUtility;

/**
 * Created by Administrator
 * this class is used to map data in the sheet "ReplyShortMessageToOneContact"
 */
public class DataReplyShortMessageToOneContact {
    ExcelUtility excelobj;
    static final String SHEETNAME="REPLYSHORTMESSAGETOONECONTACT";
    public DataReplyShortMessageToOneContact(String fileName) throws IOException, IOException {
        excelobj = new ExcelUtility(fileName, SHEETNAME);
    }


    /**
     * this function is used for getting the account which receive the message.
     * @return account receive the message.
     */
    public String getAccountReceive(){
        return excelobj.getValueAt(1,1);
    }


    /**
     * this function is used for getting the message content.
     * @return content of send message.
     */
    public String getMessageSentContent(){
        return excelobj.getValueAt(2,1);
    }


    /**
     * this function is usde for getting the message which is used replied.
     * @return the content of message which is replied
     */
    public String getMessageReplyContent(){
        return excelobj.getValueAt(3,1);
    }
}
