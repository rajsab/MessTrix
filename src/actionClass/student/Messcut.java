package actionClass.student;

import java.sql.Connection;
import DatabaseConnection.sqlConnectivity;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import org.apache.struts2.components.ActionError;
import org.joda.time.DateTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.xerces.internal.impl.dv.xs.DayDV;
import org.joda.time.Days;
public class Messcut extends ActionSupport {
private String start,end,roll,messname;
sqlConnectivity s=new sqlConnectivity();
public void setDays(int days) {
	this.days = days;
}
private int days;
public int getDays() {
	return days;
}
public String getStart() {
	return start;
}
public void setStart(String start) {
	this.start = start;
}

/**
 * @return the end
 */
public String getEnd() {
	return end;
}
public String getMessname() {
	return messname;
}
public void setMessname(String messname) {
	this.messname = messname;
}
/**
 * @param end the end to set
 */
public void setEnd(String end) {
	this.end = end;
}
public String execute(){
	
	SimpleDateFormat formater=new SimpleDateFormat("mm-dd-yyyy");
	
	Map session = ActionContext.getContext().getSession();

	messname=(String) session.get("a");

		try{
				if(messname==null){
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				return "failure";
			}
			Calendar cal=Calendar.getInstance();
			int cut=0;
			int month=cal.get(Calendar.MONTH);
			month++;
			
			String Conname,uname,pwd;
			Conname=s.sql_connection;
			uname=s.uname;
			pwd=s.pwd;
			Connection con=DriverManager.getConnection(Conname,uname,pwd);
						
			String sql2="select mess_days from mess_enrollment where s_roll= ? and date_of_joining like ?  and mess_name= ?";
			PreparedStatement ps=con.prepareStatement(sql2);
			ps.setString(1, roll);
			ps.setString(2, "%-_"+month+"-%");
			ps.setString(3, messname);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				cut=Integer.parseInt(rs.getString("mess_days"));	
				System.out.print(cut);
			}
			cut=cut-days;
			if(cut<19 || cut<20){
				addActionError("Mess-cuts is greater than 10");
			}
			else{
				String sql="update mess_enrollment set mess_days= ? where s_roll=? and date_of_joining like ?  and mess_name=?";
				PreparedStatement ps2=con.prepareStatement(sql);
				ps2.setLong(1, cut);
				ps2.setString(2, roll);
				ps2.setString(3, "%-_"+month+"%");
				ps2.setString(4, messname);
				ps2.executeUpdate();
			}
		}
		catch(SQLException e){
			System.out.print(e);
			
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				return "failure";
			
		}

	
	System.out.println(days);
	return "success";
}

/**
 * @return the roll
 */
public String getRoll() {
	return roll;
}
/**
 * @param roll the roll to set
 */
public void setRoll(String roll) {
	this.roll = roll;
}

public void validate(){
	if(roll.length()==0){
		addActionError("Please specify roll");
	}
	SimpleDateFormat formater=new SimpleDateFormat("mm-dd-yyyy");
	if(messname==null){
		((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
		ActionContext.getContext().getSession().clear();//clear session value
	}
	if(start.length()==0 || end.length()==0){
		addActionError("Please specify date");
	}
	Date a1,a2;
	try {
		a1=formater.parse(start);
		a2=formater.parse(end);
		DateTime day1=new DateTime(a1);
		DateTime day2=new DateTime(a2);
		days=Days.daysBetween(day1,day2).getDays();
		if(days>10){
			addActionError("Days are greater than 10");
		}
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

}
