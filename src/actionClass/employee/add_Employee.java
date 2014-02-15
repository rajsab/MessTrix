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
		String date=df.format(d);
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
		String sql="insert into employee(name,wage,mob,gender,mess_name,D_O_J) values ('"+ename+"','"+wage+"','"+mob+"','"+gender+"','"+messname+"','"+date+"')";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.executeUpdate();
	//	System.out.println("I am in add_employee "+messname);
		send="success";
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
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
