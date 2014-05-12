package actionClass.employee;
import java.sql.*;
import com.opensymphony.xwork2.ActionSupport;
import DatabaseConnection.sqlConnectivity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.components.ActionError;
import org.apache.struts2.interceptor.*;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
public class payment_Employee extends ActionSupport{
	private String empname,eid,messname;
	private int wage;
	private ArrayList<String>ename=new ArrayList<String>();
	sqlConnectivity sqlvar=new sqlConnectivity();

	public ArrayList<String> getEname() {
		return ename;
	}

	public void setEname(ArrayList<String> ename) {
		this.ename = ename;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	Date d=new Date();
	DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
	String send="fail";
	public String execute(){
		
		Map session = ActionContext.getContext().getSession();

		messname=(String) session.get("a");
		if(messname==null || messname.isEmpty()){
			((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
			ActionContext.getContext().getSession().clear();//clear session value
			return "failure";
		}else{
		String date=df.format(d);
		try{
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
			Connection con=DriverManager.getConnection(sqlvar.sql_connection,sqlvar.uname,sqlvar.pwd);
				if(empname.isEmpty() || empname==null){
					addActionError("Select employee name");
				}
				System.out.println("employee  "+empname+empname.length());
				String sql1="select id,wage from employee where name=? and mess_name=?";
				String sql2="insert into payment(eid,wage,mess_name,D_O_P) values (?,?,?,?)";	
				PreparedStatement ps2=con.prepareStatement(sql2);
				PreparedStatement ps3=con.prepareStatement(sql1);
				ps3.setString(1, empname);
				ps3.setString(2, messname);
				ResultSet rs=ps3.executeQuery();
				if(rs.next()){
					eid=rs.getString("id");
					wage=Integer.parseInt(rs.getString("wage"));
				}
				else{
					addActionError("Sorry, Employee not registered");
				}
				ps2.setString(1, eid);
				ps2.setInt(2, wage);
				ps2.setString(3, messname);
				ps2.setString(4, date);
				ps2.executeUpdate();
				send="success";
			}
		catch(SQLException e){
			System.out.println(e);
			
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				return "failure";
			
		}
		}
		

	return send;
	}

	public void setWage(int wage) {
		this.wage = wage;
	}

	/**
	 * @return the mob
	 */

	public int getWage() {
		return wage;
	}

	/**
	 * @return the name
	 */
	
	public String getname(){
		try {
			Map session = ActionContext.getContext().getSession();

			messname=(String) session.get("a");
			
			Connection con=DriverManager.getConnection(sqlvar.sql_connection,sqlvar.uname,sqlvar.pwd);
			
			String sql2="select name from employee where mess_name=?";
			PreparedStatement ps=con.prepareStatement(sql2);
			
			System.out.print("Ok i am fine here");
			ps.setString(1, messname);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				ename.add(rs.getString("name"));
			}
			return "filled";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "failure";
	}
	

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public void validate(){
		if(empname.isEmpty() || empname==null){
			addActionError("Select employee name");
		}
	}
}
