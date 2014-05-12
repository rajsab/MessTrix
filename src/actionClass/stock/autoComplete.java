package actionClass.stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import DatabaseConnection.sqlConnectivity;
public class autoComplete {
private ArrayList<String> itemId= new ArrayList<String>();
private ArrayList<String> vendorname= new ArrayList<String>();
Connection con;
String messname;
sqlConnectivity s=new sqlConnectivity();
public String getMessname() {
	Map session=ActionContext.getContext().getSession();
	messname=(String)session.get("a");
	return messname;
}

public void setMessname(String messname) {
	
	this.messname = messname;
}

/**
 * @return the itemId
 */
public ArrayList<String> getItemId() {
	return itemId;
}

public ArrayList<String> getVendorname() {
	return vendorname;
}

public void setVendorname(ArrayList<String> vendorname) {
	this.vendorname = vendorname;
}

/**
 * @param itemId the itemId to set
 */
public void setItemId(ArrayList<String> itemId) {
	this.itemId = itemId;
}
public String execute(){
	
	try {
		if(getMessname()==null){
			((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
			ActionContext.getContext().getSession().clear();//clear session value
			return "failure";
		}else{
			String Conname,uname,pwd;
			Conname=s.sql_connection;
			uname=s.uname;
			pwd=s.pwd;
			Connection con=DriverManager.getConnection(Conname,uname,pwd);
						
		String sql2="select item_id from item where mess_name=? ";
		PreparedStatement ps2=con.prepareStatement(sql2);
		ps2.setString(1, getMessname());
		ResultSet rs=ps2.executeQuery();
		while(rs.next()){
			itemId.add(rs.getString("item_id"));
		}
		return "success";}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
		ActionContext.getContext().getSession().clear();//clear session value
		
	}
	return "failure";
}
public String fillVendor(){
	try {
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
		String sql="select id from vendor where mess_name=?";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, getMessname());
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			vendorname.add(rs.getString("id"));
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return "success";
}
}
