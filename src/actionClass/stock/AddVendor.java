package actionClass.stock;

import java.sql.*;

import DatabaseConnection.sqlConnectivity;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddVendor extends ActionSupport {

	private String name,mob,id;
	String temp="failure",messname;
	sqlConnectivity s=new sqlConnectivity();
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
			String Conname,uname,pwd;
			Conname=s.sql_connection;
			uname=s.uname;
			pwd=s.pwd;
			Connection con=DriverManager.getConnection(Conname,uname,pwd);
						
			String sql="insert into vendor (id,name,mob,mess_name) values (?,?,?,?)";
			
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1, hold);
			ps.setString(2, name);
			ps.setString(3,mob);
			ps.setString(4,messname);
			ps.executeUpdate();
			temp="success";}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(e.getSQLState().startsWith("23")){
				addActionError("ID Already exixt");
				return "input";
			}
			e.printStackTrace();
			
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				return "failure";
			
		}
		return temp;
	}
	
	
	public void validate(){
		if(getName().length()==0){
			addFieldError("name", "Please fill Vendor name");
		}
		if(getName().length()>10){
			addFieldError("name", "too big name");
		}
		if(getMob().length()>0 && getMob().length()!=10){
			addFieldError("mob", "Mobile number must be 10 digits");
		}
		if(getId().length()==0){
			addFieldError("id", "Please give vendor Id");
		}
		Pattern m=Pattern.compile("[^0-9]",Pattern.CASE_INSENSITIVE);
		Matcher mi=m.matcher(mob);
		boolean c=mi.find();
		if(c){
			addFieldError("mob", "Mobile number must be Numbers not alphabets or charecters");
		}
		Pattern p=Pattern.compile("[^(a-z)\\s]",Pattern.CASE_INSENSITIVE);
		Matcher ma=p.matcher(name);
		boolean b=ma.find();
		if(b){
			addFieldError("ename", "Name shouldn't contain any % or special charecters");
		}
	}
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name.toUpperCase();
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
