package actionClass.stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

public class autoComplete {
private ArrayList<String> itemId= new ArrayList<String>();

/**
 * @return the itemId
 */
public ArrayList<String> getItemId() {
	return itemId;
}

/**
 * @param itemId the itemId to set
 */
public void setItemId(ArrayList<String> itemId) {
	this.itemId = itemId;
}
public String execute(){
	Connection con;
	Map session=ActionContext.getContext().getSession();
	try {
		String messname=null;
		messname=(String)session.get("a");
		if(messname==null){
			((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
			ActionContext.getContext().getSession().clear();//clear session value
			return "failure";
		}else{
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
		String sql2="select item_id from item where mess_name=? ";
		PreparedStatement ps2=con.prepareStatement(sql2);
		ps2.setString(1, messname);
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
}
