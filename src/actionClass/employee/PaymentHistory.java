package actionClass.employee;
import DatabaseConnection.sqlConnectivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import java.sql.PreparedStatement;
import com.opensymphony.xwork2.ActionContext;

class emp{
	private String name,id,salary;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}
	
}
public class PaymentHistory {
	public String messname;
	private String curMonth;
	sqlConnectivity s=new sqlConnectivity();
	private ArrayList<emp> detail=new ArrayList<emp>();
	Map<?, ?> session = ActionContext.getContext().getSession();
	public String execute(){
		messname=(String) session.get("a");
		if(messname==null){
			((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
			ActionContext.getContext().getSession().clear();//clear session value
			return "failure";
		}else{
		try {
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
						
			String sql="select p.eid,e.name,sum(p.wage) as salary from payment p,employee e where D_O_P like ? and e.id=p.eid and p.mess_name=? group by (p.eid)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,"%-_"+curMonth+"-%");
			ps.setString(2,messname);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				emp a=new emp();
				a.setId(rs.getString("p.eid"));
				a.setName(rs.getString("e.name"));
				a.setSalary(rs.getString("salary"));
				detail.add(a);
			}
			return "success";
		} catch (SQLException e) {
			e.printStackTrace();
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				return "failure";
			
		}}
		
	}
	public String getCurMonth() {
		return curMonth;
	}
	
	public void setCurMonth(String curMonth) {
		this.curMonth = curMonth;
	}
	public ArrayList<emp> getDetail() {
		return detail;
	}
	public void setDetail(ArrayList<emp> detail) {
		this.detail = detail;
	}
	
	
}