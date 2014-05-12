package actionClass.student;
import DatabaseConnection.sqlConnectivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class deleteExtra extends ActionSupport{
Connection con;
sqlConnectivity s=new sqlConnectivity();
private String selected_id;
private String itemid,roll;
public String getRoll() {
	return roll;
}
public void setRoll(String roll) {
	this.roll = roll;
}
private int quantity,price;

public String getItemid() {
	return itemid;
}
public void setItemid(String itemid) {
	this.itemid = itemid;
}
public String getSelected_id() {
	return selected_id;
}
public void setSelected_id(String selected_id) {
	this.selected_id = selected_id;
}
public String execute(){
	Map session=ActionContext.getContext().getSession();
	String messname=(String)session.get("a");
	System.out.println("hello");
	System.out.print(""+selected_id);
	
	
	Date d=new Date();
	Calendar cal=Calendar.getInstance();
	DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
	cal.add(Calendar.MONTH, -1);
	cal.set(Calendar.DATE, 1);
	String monthfirstdate=df.format(cal.getTime());
	
	try {
		String Conname,uname,pwd;
		Conname=s.sql_connection;
		uname=s.uname;
		pwd=s.pwd;
		con=DriverManager.getConnection(Conname,uname,pwd);
					
		con.setAutoCommit(false);
		
		String sql="select itemid,quantity,roll from extra where id=?";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, selected_id);
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			itemid=rs.getString("itemid");
			quantity=Integer.parseInt(rs.getString("quantity"));
			roll=rs.getString("roll");
		}
		if(itemid.matches(".*daily")){
			System.out.print("hello i am inside matches");
			return "sorry";
		}
		String sql2="select price from item where item_id=?";
		ps=con.prepareStatement(sql2);
		ps.setString(1, itemid);
		ResultSet rs2=ps.executeQuery();
		System.out.println(itemid+" "+quantity+" "+roll);
		while(rs2.next()){
			String temp=rs2.getString("price");
			price=Integer.parseInt(temp);
		}
		int total=0;
		total=quantity*price;
		String sql3="update item_extra set quantity=quantity+? where item_id=?";
		ps=con.prepareStatement(sql3);
		ps.setInt(1, quantity);
		ps.setString(2, itemid);
		ps.executeUpdate();
		String sql4="update mess_enrollment set extra=extra-? where s_roll=? and mess_name=? and date_of_joining>? ";
		ps=con.prepareStatement(sql4);
		ps.setInt(1, total);
		ps.setString(2, roll);
		ps.setString(3, messname);
		ps.setString(4, monthfirstdate);
		ps.executeUpdate();
		String sql1="delete from extra where id=?";
		PreparedStatement ps1=con.prepareStatement(sql1);
		ps1.setString(1, selected_id);
		ps1.executeUpdate();
		con.commit();
		return "success";
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		try {
			con.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	return "failure";
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public int getPrice() {
	return price;
}
public void setPrice(int price) {
	this.price = price;
}
public void validate(){
	
}
}
