package actionClass.employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
public class ShowcomboBox extends ActionSupport{

	private String messname,eid,ename,wage,mob,mname;
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getWage() {
		return wage;
	}
	public void setWage(String wage) {
		this.wage = wage;
	}
	public String getMob() {
		return mob;
	}
	public void setMob(String mob) {
		this.mob = mob;
	}
	private ArrayList name=new ArrayList();

	/**
	 * @re1turn the name
	 */
		public ShowcomboBox(){
			Map session = ActionContext.getContext().getSession();

			messname=(String) session.get("a");
			if(messname==null){
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				
			}else{
			try {
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
				String sql="select name from employee where mess_name=?";
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setString(1, messname);
				ResultSet rs=ps.executeQuery();
				while(rs.next()){
					name.add(rs.getString("name"));
				
				}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
					((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
					ActionContext.getContext().getSession().clear();//clear session value
				
				
			}}
			
		}
		
	public String execute(){
		String temp="failure";

		Map session = ActionContext.getContext().getSession();

		messname=(String) session.get("a");
		
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
			String sql="select id,wage,mob from employee where mess_name='"+messname+"' and name='"+ename+"'";
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				eid=rs.getString("id");	
				wage=rs.getString("wage");
				mob=rs.getString("mob");
					}
			temp="success";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Employee id =" + eid);
		return temp;
	}
	public String display_combobox(){
		return "display";
	}

	public String getMessname() {
		return messname;
	}

	public void setMessname(String messname) {
		this.messname = messname;
	}

	public ArrayList getName() {
		return name;
	}

	public void setName(ArrayList name) {
		this.name = name;
	}
	/**
	 * @return the eid
	 */
	public String getEid() {
		return eid;
	}
	/**
	 * @param eid the eid to set
	 */
	public void setEid(String eid) {
		this.eid = eid;
	}
	/**
	 * @return the mname
	 */
	public String getMname() {
		return mname;
	}
	/**
	 * @param mname the mname to set
	 */
	public void setMname(String mname) {
		this.mname = mname;
	}
	
}
