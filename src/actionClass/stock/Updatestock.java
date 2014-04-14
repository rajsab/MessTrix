package actionClass.stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

public class Updatestock {
private String id;
private int quantity;
private String d_o_p;
	public String execute(){
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal=Calendar.getInstance();
		String now=df.format(cal.getTime());
		Map session=ActionContext.getContext().getSession();
		String messname=(String)session.get("a");
		if(messname==null){
			((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
			ActionContext.getContext().getSession().clear();//clear session value
			return "failure";
		
		}
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
			String sql3="insert into item_entry values (?,?,?)";
			PreparedStatement ps3=con.prepareStatement(sql3);
			ps3.setString(1, getId());
			ps3.setString(2, getD_o_p());
			ps3.setInt(3, getQuantity());
			ps3.executeUpdate();
			return "success";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
			ActionContext.getContext().getSession().clear();//clear session value
			return "failure";
		
		}
		
		
		
	}
	public String display() {
		return "success";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getD_o_p() {
		return d_o_p;
	}
	public void setD_o_p(String d_o_p) {
		this.d_o_p = d_o_p;
	}
}
