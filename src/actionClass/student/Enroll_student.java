package actionClass.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Enroll_student extends ActionSupport{
	private String roll,name,card_number;
	public String getRoll() {
		return roll;
	}
	public void setRoll(String roll) {
		this.roll = roll;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	Date d=new Date();
	DateFormat df= new SimpleDateFormat("yyyy-MM-dd");

	String temp="failure",messname;
	Map session=ActionContext.getContext().getSession();
	public String execute(){
		try {
			messname=(String) session.get("a");
			if(messname==null){
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				return "failure";
			}
			String date;
			date=df.format(d);
			String id;
			id=roll+messname+date;
			Calendar cal=new GregorianCalendar();
			int numDays =0;
					numDays=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			System.out.print("Days"+numDays);
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
			String sql="insert into mess_enrollment (id,s_roll,s_name,date_of_joining,card_no,mess_name,mess_days) values ('"+id+"','"+roll+"','"+name+"','"+date+"','"+card_number+"','"+messname+"','"+numDays+"')";
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, roll);
			ps.setString(4, date);
			ps.setString(5, card_number);
			ps.setString(6, messname);
			ps.setInt(7, numDays);
			ps.executeUpdate();
			temp="success";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				return "failure";
			
		}
		return temp;
	}
}
