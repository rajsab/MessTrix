package actionClass;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.*;
import DatabaseConnection.sqlConnectivity;;
public class LoginAction extends ActionSupport implements SessionAware{
	private String username,password,userid;
	private String m_name;
	String temp="input",id;
	private Map v;
	public Map getV() {
		return v;
	}
	public void setV(Map v) {
		this.v = v;
	}
	Process runcommand;
	DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
	Calendar cal=Calendar.getInstance();
	String now=df.format(cal.getTime());
	sqlConnectivity conn=new sqlConnectivity();
	public String execute(){
			try{
				
				sqlConnectivity s=new sqlConnectivity();
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
				Connection con=DriverManager.getConnection(Conname,uname,pwd);
				String sql="select username from login where userid=? and password=? and mess_name=?";
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setString(1, userid);
				ps.setString(2, password);
				ps.setString(3, m_name);
				ResultSet rs=ps.executeQuery();
				//System.out.println("here");
			//	System.out.println(userid+" "+m_name+" "+password);
				
				while(rs.next()){
					//System.out.print("Hello");
					username=rs.getString(1);
					//System.out.println(userid);
					v.put("a", m_name);
					//v.put("name", m_name);
					temp= "success";}
				sql="delete from update_daily_stock where entry_date < ?";
				ps=con.prepareStatement(sql);
				ps.setString(1, now);
				ps.executeUpdate();
				//String[] command= new String[]{"sh","-c"," mysqldump --user="+uname+" --password="+pwd+" --databases messproject > /home/raj/Desktop/messproject.sql"};
				String[] command= new String[]{"sh","-c"," mysqldump --user="+uname+" --password="+pwd+" --databases messproject > /var/lib/tomcat7/webapps/MessAutomation/messproject.sql"};
				try {
					runcommand=Runtime.getRuntime().exec(command);
					int wait=1;
					try {
						wait=runcommand.waitFor();
						if(wait==0){
							System.out.println("Backup created");
						}
						if(wait==1){
							System.out.println("Unable to create");
						}
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			catch(SQLException e){
				System.out.print(e);
			}
			
			return temp;
	}
	public void validate(){
		if(userid.length()==0){
			addFieldError("userid", "Please fill correct username");
		}
		if(password.length()==0){
			addFieldError("password", "Please fill correct password");
		}
		if(getM_name().length()==0){
			addFieldError("m_name", "Please fill correct messname");
		}
	}
		public String getUsername() {
			return username;
		}
		public void setUserid(String username) {
			this.userid = username.toUpperCase();
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
