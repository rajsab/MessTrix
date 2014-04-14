package test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal=Calendar.getInstance();
		
		int month=cal.get(Calendar.MONTH);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		//System.out.println(df.format(cal.getTime()));*/
		System.out.println(month);

	}

}
