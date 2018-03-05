package DataMapping;

import java.io.IOException;

import CustomizeLibrary.ExcelUtility;

/**
 * Created by Administrator
 * this class is used to mapped with the data in the sheet "ForwardShortMessageToGroup"
 */
public class DataForwardShortMessageToGroup {
    ExcelUtility excelobj;
    final  static String SHEETNAME="FORWARDSHORTMESSAGETOGROUP";
    public DataForwardShortMessageToGroup(String fileName) throws IOException {
        excelobj=new ExcelUtility(fileName,SHEETNAME);
    }


    /**
     * this function is used for getting the group name which receive the forward message
     * @return group name
     */
    public String getGroupNameToReceiveForwardMessage(){
        return excelobj.getValueAt(1,1);
    }

    /**
     * this function is used for getting the Account (on device 02) which forwards message to Group
     * @return account used for forwarding the message to group.
     */
    public String getAccountForwardMessage(){
        return excelobj.getValueAt(2,1);
    }


    /**
     * this function is used for getting of content of message which is used for sending message(device 01 send to device 02)
     * * @return content of message
     */
    public String getContentOfMessageSent(){
        return excelobj.getValueAt(3,1);
    }


    /**
     * this functio is used for getting the content of message which is forwarded (device 02 forward to device 01)
     * @return the content of forward message.
     */
    public String getContentOfMessageForward(){
        return excelobj.getValueAt(4,1);
    }



}
