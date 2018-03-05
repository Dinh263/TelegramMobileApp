package DataMapping;

import java.io.IOException;

import CustomizeLibrary.ExcelUtility;

/**
 * Created by Administrator
 * this class is used to map data in the sheet "SendShortMessageToGroup"
 */
public class DataSendShortMessageToGroup {
    ExcelUtility excelobj;
    static final String SHEETNAME="SENDSHORTMESSAGETOGROUP";
    public DataSendShortMessageToGroup(String fileName) throws IOException, IOException {
        excelobj = new ExcelUtility(fileName, SHEETNAME);
    }


    /**
     * this function is used for getting the group name which is receive the massage.
     * device 01 send short message to this group.
     * @return the group name which receive massage.
     */
    public String getGroupName(){
        return excelobj.getValueAt(1,1);
    }


    /**
     * this function is used for getting the content message which is used for sending
     * @return the content of message.
     */
    public String getMessageContent(){
        return excelobj.getValueAt(2,1);
    }
}
