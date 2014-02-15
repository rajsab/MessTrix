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
	private String name,mob,messname;
	String eid,ewage;
	Date d=new Date();
	DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
	String send="fail";
	public String execute(){
		
		Map session = ActionContext.getContext().getSession();

		messname=(String) session.get("a");
		String date=df.format(d);
		try{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
			String sql1="select wage,id from employee where name='"+name+"' and mob='"+mob+"' and mess_name='"+messname+"'";
			PreparedStatement ps=con.prepareStatement(sql1);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				ewage=rs.getString("wage");
				eid=rs.getString("id");
				System.out.println("EWAGE= "+ewage+" EID "+eid);
				
				String sql2="insert into payment(eid,wage,mess_name,D_O_P) values ('"+eid+"','"+ewage+"','"+messname+"','"+date+"')";	
				PreparedStatement ps2=con.prepareStatement(sql2);
				ps2.executeUpdate();
				send="success";
			}
			
		
		}
		catch(SQLException e){
			System.out.print(e);
		}
			
	return send;
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
