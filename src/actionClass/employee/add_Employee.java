package actionClass.employee;
import java.sql.*;

import DatabaseConnection.sqlConnectivity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import DatabaseConnection.sqlConnectivity;;
public class add_Employee extends ActionSupport{
sqlConnectivity s=new sqlConnectivity();
//Connection c=s.getCon();
private String messname,ename,mob,gender;
private int wage;
Date d=new Date();
DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
Map session = ActionContext.getContext().getSession();

public String execute(){
	try {
			messname=(String) session.get("a");
			String Conname,uname,pwd;
			Conname=s.sql_connection;
			uname=s.uname;
			pwd=s.pwd;
			String date=df.format(d);
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Connection con=DriverManager.getConnection(Conname,uname,pwd);
			String sql="insert into employee(name,wage,mob,gender,mess_name,D_O_J) values (?,?,?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, ename);
			ps.setInt(2, wage);
			ps.setString(3, mob);
			ps.setString(4, gender);
			ps.setString(5, messname);
			ps.setString(6, date);
		ps.executeUpdate();
	//	System.out.println("I am in add_employee "+messname);
		return "success";
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
			((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
			ActionContext.getContext().getSession().clear();//clear session value
			
		
	}
	return "failure";
}

public void validate(){
	
	if(getEname().length()==0){
		addFieldError("ename", "Please fill Employee name");
	}
	if(wage==0){
		addFieldError("wage", "wage can't be 0");
	}
	if(getMob().length()>0 && getMob().length()!=10){
		addFieldError("mob", "Mobile number must be 10 digits");
	}
	Pattern m=Pattern.compile("[^0-9]",Pattern.CASE_INSENSITIVE);
	Matcher mi=m.matcher(mob);
	boolean c=mi.find();
	if(c){
		addFieldError("mob", "Mobile number must be Numbers not alphabets or charecters");
	}
	Pattern p=Pattern.compile("[^(a-z)\\s]",Pattern.CASE_INSENSITIVE);
	Matcher ma=p.matcher(ename);
	boolean b=ma.find();
	if(b){
		addFieldError("ename", "Name shouldn't contain any % or special charecters");
	}
}
public String getMessname() {
	return messname;
}
public String getEname() {
	return ename;
}
public void setEname(String ename) {
	this.ename = ename.toUpperCase();
}
public String getMob() {
	return mob;
}
public void setMob(String mob) {
	this.mob = mob;
}public String getGender() {
	return gender;
}
public int getWage() {
	return wage;
}
public void setWage(int wage) {
	this.wage = wage;
}
public void setGender(String gender) {
	this.gender = gender;
}

}
