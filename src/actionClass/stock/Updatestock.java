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
import com.opensymphony.xwork2.ActionSupport;
import DatabaseConnection.sqlConnectivity;
public class Updatestock extends ActionSupport {
	Connection con;
private String id;
private int quantity,price;
public int getPrice() {
	return price;
}
public void setPrice(int price) {
	this.price = price;
}
private String d_o_p;
	public String execute(){
		
		sqlConnectivity s=new sqlConnectivity();
		String Conname,uname,pwd;
		Conname=s.sql_connection;
		uname=s.uname;
		pwd=s.pwd;
		
					
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
			con=DriverManager.getConnection(Conname,uname,pwd);
			con.setAutoCommit(false);
			String sql3="insert into item_entry values (?,?,?)";
			PreparedStatement ps3=con.prepareStatement(sql3);
			ps3.setString(1, getId());
			ps3.setString(2, getD_o_p());
			ps3.setInt(3, getQuantity());
			ps3.executeUpdate();
			String sql4="update item_extra set quantity=quantity+? where item_id=?";
			ps3=con.prepareStatement(sql4);
			ps3.setInt(1, getQuantity());
			ps3.setString(2, getId());
			ps3.executeUpdate();
			sql4="update item set price=? where item_id =?";
			ps3=con.prepareStatement(sql4);
			ps3.setInt(1, price);
			ps3.setString(2, getId());
			ps3.executeUpdate();
			con.commit();
			return "success";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				con.rollback();
				if(e.getSQLState().startsWith("23")){
					addActionError("Can't put same item twice");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
	public void validate(){
		if(getQuantity()==0){
			addFieldError("quantity","Quantity can't be zero");
		}
		if(getPrice()==0){
			addFieldError("price","price can't be zero");
		}
	}
}
