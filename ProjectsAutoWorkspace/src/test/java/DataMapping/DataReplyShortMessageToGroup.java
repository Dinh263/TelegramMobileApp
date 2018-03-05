package DataMapping;

import java.io.IOException;

import CustomizeLibrary.ExcelUtility;

/**
 * Created by Administrator
 * this class is used to map data in the sheet "ReplyShortMessageToGroup"
 */
public class DataReplyShortMessageToGroup {
    ExcelUtility excelobj;
    final static String SHEETNAME="REPLYSHORTMESSAGETOGROUP";
    public DataReplyShortMessageToGroup(String fileName) throws IOException {
        excelobj=new ExcelUtility(fileName,SHEETNAME);
    }


    /**
     * This function is used for getting the group name which receive message.
     * @return group name which receive massage
     */
    public String getGroupName(){
        return excelobj.getValueAt(1,1);
    }


    /**
     * this function is used for getting the account name which reply the massage to group
     * @return the account name which reply the massage to group
     */
    public String getAccountReceive(){
        return excelobj.getValueAt(2,1);
    }


    /**
     * this function is used for getting the content of sending message.
     * @return content of sending message
     */
    public String getMessageSentContent(){
        return excelobj.getValueAt(3,1);
    }


    /**
     * this function is used for getting the content of replying message.
     * @return the content of reply message.
     */
    public String getMessageReplyContent(){
        return excelobj.getValueAt(4,1);
    }


}
