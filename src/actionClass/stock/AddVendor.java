package actionClass.stock;

import java.sql.*;
import java.util.Map;

import org.apache.catalina.Session;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddVendor extends ActionSupport {

	private String name,mob,id;
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
			else{
			String hold;
			hold=messname+id;
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
			String sql="insert into vendor (id,name,mob,mess_name) values ('"+hold+"','"+name+"','"+mob+"','"+messname+"')";
			PreparedStatement ps= con.prepareStatement(sql);
			ps.executeUpdate();
			temp="success";}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				return "failure";
			
		}
		return temp;
	}
	
	
	
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the mob
	 */
	public String getMob() {
		return mob;
	}

	/**
	 * @param mob the mob to set
	 */
	public void setMob(String mob) {
		this.mob = mob;
	}
	
	public String getId() {
		return id;
	}

	/**
	 * @param mob the mob to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}
