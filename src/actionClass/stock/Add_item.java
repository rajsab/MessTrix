package actionClass.stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

public class Add_item {
	private String name,vendor,id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private int quantity,price;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
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
	String temp="failure",messname;
	Map session=ActionContext.getContext().getSession();
	
	public String execute(){
		try {
			messname=(String) session.get("a");
			if(messname==null){
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				return "failure";
			}
			else{
			String hold;
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal=Calendar.getInstance();
			String now=df.format(cal.getTime());
			hold=messname+id;
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
			String sql="insert into item (item_id,name,price,vender,mess_name) values (?,?,?,?,?)";
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1, hold);
			ps.setString(2,name);
			//ps.setInt(3, quantity);
			ps.setInt(3, price);
			ps.setString(4, vendor);
			ps.setString(5, messname);
			ps.executeUpdate();
			/*String sql3="insert into item_entry (item_id,id) values (?,?)";
			PreparedStatement ps3=con.prepareStatement(sql3);
			ps3.setString(1, id);
			ps3.setString(2, now);
		
			ps3.executeUpdate();
			*/
			temp="success";}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				return "failure";
			
		}
		return temp;
	}
	
}
