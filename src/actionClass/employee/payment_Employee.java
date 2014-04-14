package actionClass.employee;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.interceptor.*;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
public class payment_Employee extends ActionSupport{
	private String name,eid,messname,wage;
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
		if(messname==null){
			((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
			ActionContext.getContext().getSession().clear();//clear session value
			return "failure";
		}else{
		String date=df.format(d);
		try{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
				String sql2="insert into payment(eid,wage,mess_name,D_O_P) values (?,?,?,?)";	
				PreparedStatement ps2=con.prepareStatement(sql2);
				ps2.setString(1, eid);
				ps2.setString(2, wage);
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

	/**
	 * @return the mob
	 */

	public String getWage() {
		return wage;
	}

	public void setWage(String wage) {
		this.wage = wage;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	

}
