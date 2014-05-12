package actionClass.student;

import java.sql.DriverManager;
import DatabaseConnection.sqlConnectivity;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import actionClass.stock.autoComplete;
public class showRoll {
Connection con;
private ArrayList<String> roll=new ArrayList<String>();
private ArrayList<String> itemId=new ArrayList<String>();
Map session=ActionContext.getContext().getSession();
autoComplete a= new autoComplete();
String ack="failure";
sqlConnectivity s=new sqlConnectivity();
public String execute(){
	try {
		Date d=new Date();
		String messname=(String)session.get("a");
		DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal=Calendar.getInstance();
		cal.setTime(d);
		int month=cal.get(Calendar.MONTH);
		cal.set(Calendar.DAY_OF_MONTH,1);
		String monthfirstdate=df.format(cal.getTime());
		
		
		String Conname,uname,pwd;
		Conname=s.sql_connection;
		uname=s.uname;
		pwd=s.pwd;
		con=DriverManager.getConnection(Conname,uname,pwd);
				
		String sql="select s_roll from mess_enrollment where date_of_joining>=? and mess_name=?";
		String sql2="select item_id from item where mess_name=?";
		String sql3="select id from update_daily_stock where id like ?";
				PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, monthfirstdate);
		ps.setString(2, messname);
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			roll.add(rs.getString("s_roll"));
		}
		ps=con.prepareStatement(sql2);
		ps.setString(1, messname);
		rs=ps.executeQuery();
		while(rs.next()){
			//System.out.print("Getting it--");
			itemId.add(rs.getString("item_id"));
		}
		ps=con.prepareStatement(sql3);
		ps.setString(1, messname+"%");
		rs=ps.executeQuery();
		
		while(rs.next()){
			System.out.print("Getting it--");
			itemId.add(rs.getString("id"));
		}
		return "success";
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return "failure";
}
public ArrayList<String> getRoll() {
	return roll;
}
public void setRoll(ArrayList<String> roll) {
	this.roll = roll;
}
/**
 * @return the itemId
 */
public ArrayList<String> getItemId() {
	return itemId;
}
/**
 * @param itemId the itemId to set
 */
public void setItemId(ArrayList<String> itemId) {
	this.itemId = itemId;
}
}
