package actionClass.employee;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import org.apache.catalina.Session;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;

import DatabaseConnection.sqlConnectivity;;
public class add_Employee extends ActionSupport{
sqlConnectivity s=new sqlConnectivity();
Connection c=s.getCon();
private String messname,ename,mob,gender;
private String wage;
Date d=new Date();
DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
String send="fail";
Map session = ActionContext.getContext().getSession();

public String execute(){
	try {
			messname=(String) session.get("a");
			if(messname==null){
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				return "failure";
			}
			else{
			String date=df.format(d);
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
			String sql="insert into employee(name,wage,mob,gender,mess_name,D_O_J) values (?,?,?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, ename);
			ps.setString(2, wage);
			ps.setString(3, mob);
			ps.setString(4, gender);
			ps.setString(5, messname);
			ps.setString(6, date);
		ps.executeUpdate();
	//	System.out.println("I am in add_employee "+messname);
		send="success";
	}} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
			((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
			ActionContext.getContext().getSession().clear();//clear session value
			return "failure";
		
	}
	return send;
}
public String getMessname() {
	return messname;
}
public String getEname() {
	return ename;
}
public void setEname(String ename) {
	this.ename = ename;
}
public String getMob() {
	return mob;
}
public void setMob(String mob) {
	this.mob = mob;
}
public String getWage() {
	return wage;
}
public void setWage(String wage) {
	this.wage = wage;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}


}
