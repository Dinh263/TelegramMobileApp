package CustomizeLibrary;

public class ShellExecuter {
public ShellExecuter(){
		
	}
	
/**
 * This function is used for executing the command  from command line of window
 * <br/>The example command is : adb devices  -- if you want to list out the current devices
 * <br/>Or command :  cd folderA   --- if you want to go to the folderA...
 * <br/>.....
 * @param command is the command which will be executed
 * @return true if executed successfully, otherwise it will return false
 */
	public boolean ExecuterCommand(String command){
		try{
			Runtime runtime=Runtime.getRuntime();
			Process p = runtime.exec(command);
			p.waitFor();
			return true;
		}
		catch(Exception ex ){
			System.out.println(ex.getMessage());
			return false;
		}
	}
}
