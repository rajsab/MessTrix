package actionClass;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.*;
import DatabaseConnection.sqlConnectivity;;
public class LoginAction extends ActionSupport implements SessionAware{
	private String username,password,userid;
	private String m_name;
	String temp="failure",id;
	Map v;
	sqlConnectivity conn=new sqlConnectivity();
	public String execute(){
			try{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
				String sql="select username from login where userid=? and password=? and mess_name=?";
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setString(1, userid);
				ps.setString(2, password);
				ps.setString(3, m_name);
				ResultSet rs=ps.executeQuery();
				//System.out.println("here");
			//	System.out.println(userid+" "+m_name+" "+password);
				
				while(rs.next()){
					System.out.print("Hello");
					username=rs.getString(1);
					//System.out.println(userid);
					v.put("a", m_name);
					//v.put("name", m_name);
					temp= "success";}
			}
			catch(SQLException e){
				System.out.print(e);
			}
			
			return temp;
	}
	public void validate(){
		if(getUserid().length()==0){
			addFieldError("userid", "Please fill correct username");
		}
		if(getPassword().length()==0){
			addFieldError("password", "Please fill correct password");
		}
	}
		public String getUsername() {
			return username;
		}
		public void setUserid(String username) {
			this.userid = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getUserid() {
			return userid;
		}
		
		@Override
		public void setSession(Map m) {
			this.v=m;
			
		}
		public String getM_name() {
			return m_name;
		}
		public void setM_name(String m_name) {
			this.m_name = m_name;
		}
}
