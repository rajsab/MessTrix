package actionClass.student;

import java.sql.Connection;
import org.joda.time.Days;
import org.joda.time.DateTime;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import DatabaseConnection.sqlConnectivity;
public class Enroll_student extends ActionSupport{
	private String roll,name,card_number;
	public String getRoll() {
		return roll;
	}
	public void setRoll(String roll) {
		this.roll = roll.toUpperCase();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name.toUpperCase();
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	Date d=new Date();
	DateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateFormat simple= new SimpleDateFormat("yyyy-MM-dd");

	String temp="failure",messname;
	Map session=ActionContext.getContext().getSession();
	public String execute(){
		
		sqlConnectivity s=new sqlConnectivity();
		try {
			messname=(String) session.get("a");
			if(messname==null){
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				return "failure";
			}
			String date;
			date=simple.format(d);
			String id;
			id=roll+messname+date;
			Calendar cal=new GregorianCalendar();
			cal=Calendar.getInstance();
			int actDays =0;
					actDays=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			//System.out.print("Days"+numDays);
			cal.add(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
			String monthfirstdate=df.format(cal.getTime());
			Date d=new Date();
			String presentdate=df.format(d);
			int curdays=0;
			Date d1=null,d2=null;
			try {
				d1 = df.parse(monthfirstdate);
				d2 =df.parse(presentdate);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				 
			DateTime dt1 = new DateTime(d1);
			DateTime dt2 = new DateTime(d2);

			curdays=Days.daysBetween(dt1,dt2).getDays();
			int numDays=actDays-curdays;String Conname,uname,pwd;
			Conname=s.sql_connection;
			uname=s.uname;
			pwd=s.pwd;
			Connection con=DriverManager.getConnection(Conname,uname,pwd);
			String sql="insert into mess_enrollment (id,s_roll,s_name,date_of_joining,card_no,mess_name,mess_days) values (?,?,?,?,?,?,?)";
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(3, name);
			ps.setString(2, roll);
			ps.setString(4, date);
			ps.setString(5, card_number);
			ps.setString(6, messname);
			ps.setInt(7, numDays);
			ps.executeUpdate();
			temp="success";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e.getSQLState().startsWith("23")){
				addActionError("Roll Number already enrolled for this month");
				return "input";
			}
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				return "failure";
			
		}
		return temp;
	}
	public void validate(){
		if(getRoll().length()==0){
			addFieldError("roll", "Please fill Roll");
		}
		Pattern p=Pattern.compile("[^a-z0-9]",Pattern.CASE_INSENSITIVE);
		Matcher ma=p.matcher(roll);
		boolean b=ma.find();
		if(b){
			addFieldError("roll", "Name shouldn't contain any % or special charecters");
		}
		if(getName().length()==0){
			addFieldError("name", "Please fill name");
		}
		Pattern pi=Pattern.compile("[^(a-z)\\s]",Pattern.CASE_INSENSITIVE);
		ma=pi.matcher(name);
		b=ma.find();
		if(b){
			addFieldError("name", "Name shouldn't contain any special charecters");
		}
		
	}
}
