package actionClass.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.sql.SQLException;
import com.opensymphony.xwork2.ActionContext;

public class Addextra {
	Date d=new Date();
	DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
	DateFormat full=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String send="fail";
	Map session = ActionContext.getContext().getSession();
	private String roll,itemid;
	private int quantity;
	public String getRoll() {
		return roll;
	}
	public void setRoll(String roll) {
		this.roll = roll;
	}
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	
	public String execute(){
		String messname,monthfirstdate,id;
		int price=0,tot;
		messname=(String) session.get("a");
		if(messname==null){
			((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
			ActionContext.getContext().getSession().clear();//clear session value
			return "failure";
		}
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(d);
		int month=cal.get(Calendar.MONTH);
		cal.set(Calendar.DAY_OF_MONTH,1);
		monthfirstdate=df.format(cal.getTime());
		try{
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
		String sql="select price from item where item_id='"+itemid+"' and mess_name='"+messname+"' ";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, itemid);
		ps.setString(2, messname);

		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			price=Integer.parseInt(rs.getString("price"));
		}
		tot=price*quantity;
		String sql2="update mess_enrollment set extra=extra+? where mess_name=? and s_roll=? and date_of_joining >?";
		Calendar cals=Calendar.getInstance();
		id=full.format(cals.getTime());
		String sql3="insert into extra (id,quantity,mess_name,itemid,roll) values (?,?,?,?,?)";
		String sql4="update item_extra set Quantity_bought=? where item_id=? ";
		PreparedStatement ps1=con.prepareStatement(sql2);
		ps1.setInt(1, tot);
		ps1.setString(2, messname);
		ps1.setString(3, roll);
		ps1.setString(4, monthfirstdate);
		ps1.executeUpdate();
		PreparedStatement ps2=con.prepareStatement(sql3);
		ps2.setString(1, id);
		ps2.setInt(2, quantity);
		ps2.setString(3, messname);
		ps2.setString(4, itemid);
		ps2.setString(5, roll);
		
		ps2.executeUpdate();
		PreparedStatement ps3=con.prepareStatement(sql4);
		ps3.setInt(1, quantity);
		ps3.setString(2, itemid);
		ps3.executeUpdate();
		System.out.println(monthfirstdate);
		send="success";}
		catch(SQLException ex){
			System.out.print(ex);
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				return "failure";
		}
		return send;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
