package actionClass.employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;;
public class editEmployee {
	private String messname,ename,mob,eid;
	public String getMessname() {
		return messname;
	}

	public void setMessname(String messname) {
		this.messname = messname;
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

	public int getWage() {
		return wage;
	}

	public void setWage(int wage) {
		this.wage = wage;
	}

	private int wage;
	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	String send="failure";
	Map session = ActionContext.getContext().getSession();

	public String execute(){
		try {
			messname=(String) session.get("a");
		//	String date=df.format(d);
			if(messname==null){
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				return "failure";
			}
			else{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
			String sql="update employee set name=?,wage=?,mob=? where id=? ";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, ename);
			ps.setInt(2, wage);
			ps.setString(3, mob);
			ps.setString(4, eid);
			
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

}
