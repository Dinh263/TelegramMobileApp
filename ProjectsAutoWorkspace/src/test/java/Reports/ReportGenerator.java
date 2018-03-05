package Reports;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;

public class ReportGenerator {
	
	public static void main(String[] args ){
		File source=new File(System.getProperty("user.dir")+"/test-output/html");
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date=new Date();
		String var=dateFormat.format(date);
		String reportDir=System.getProperty("user.dir")+"/Reports/"+var;
		File target=new File(System.getProperty("user.dir")+"/Reports/"+var);
		try{
			copyFolder(source, target);
			System.out.println("Generate report done!!!");
			System.out.println(reportDir);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void copyFolder(File sourceLocation, File targetLocation) throws IOException {
		FileUtils.copyDirectory(sourceLocation, targetLocation);
	}

}
