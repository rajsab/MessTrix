package actionClass.student;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
public class Getfile {
private InputStream file;
private String filename;
/**
 * @return the file
 */
public InputStream getFile() {
	return file;
}

public String getFilename() {
	return filename;
}


public String execute(){
	System.out.println("inside getfile");
	String messname;
	Map session=ActionContext.getContext().getSession();
	messname=(String) session.get("a");
	try {
		file=new FileInputStream(new File("/home/raj/Desktop/"+messname+".pdf"));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		System.out.println("not able to read");
	}
	filename=messname+".pdf";
	return "success";
}
}
