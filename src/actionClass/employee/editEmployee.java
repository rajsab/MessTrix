package actionClass.employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import DatabaseConnection.sqlConnectivity;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
public class editEmployee extends ActionSupport {
	private String messname,ename,mob,eid;
	private int wage;
	sqlConnectivity s=new sqlConnectivity();
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
		this.ename = ename.toUpperCase();
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
				
				String Conname,uname,pwd;
				Conname=s.sql_connection;
				uname=s.uname;
				pwd=s.pwd;
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
			String sql="update employee set name=?,mob=?,wage=? where id=? ";
			Connection con=DriverManager.getConnection(Conname,uname,pwd);
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, ename);
			ps.setString(2, mob);
			ps.setInt(3, wage);
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
	public String callf(){
		return "failure";
	}
	public void validate(){
messname=(String) session.get("a");
		
		if(messname==null || messname.isEmpty()){
			((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
			ActionContext.getContext().getSession().clear();//clear session value
			callf();
		}
		if(getEname().length()==0){
			addFieldError("ename", "Please fill full name");
		}
		if(getMob().length()>0 && getMob().length()<10){
			addFieldError("mob", "Mobile number must be equa 10");
		}
		if(wage==0){
			addFieldError("wage", "Wage shouldn't be 0");
		}
		Pattern m=Pattern.compile("[^0-9]",Pattern.CASE_INSENSITIVE);
		Matcher mi=m.matcher(mob);
		boolean c=mi.find();
		if(c){
			addFieldError("mob", "Mobile number must be Numbers not alphabets or charecters");
		}
		Pattern p=Pattern.compile("[^a-z]",Pattern.CASE_INSENSITIVE);
		Matcher ma=p.matcher(ename);
		boolean b=ma.find();
		if(b){
			addFieldError("ename", "Name shouldn't contain any % or special charecters");
		}
	}

}
