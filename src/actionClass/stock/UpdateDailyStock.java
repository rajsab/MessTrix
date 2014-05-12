package actionClass.stock;

import java.sql.Connection;
import DatabaseConnection.sqlConnectivity;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateDailyStock extends ActionSupport {
	sqlConnectivity s=new sqlConnectivity();
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	private int price,quantity;
	String messname;
	public String execute(){

		String Conname,uname,pwd;
		Conname=s.sql_connection;
		uname=s.uname;
		pwd=s.pwd;

		Connection con;
		Map session=ActionContext.getContext().getSession();
		DateFormat d=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal=Calendar.getInstance();
		String nowdate=d.format(cal.getTime());
		messname=(String) session.get("a");
		try {
			con=DriverManager.getConnection(Conname,uname,pwd);
			String sql="select id from update_daily_stock where id=?";
			String sql1;
			System.out.println(""+id);
			PreparedStatement ps;
			ps=con.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				sql1="update update_daily_stock set quantity=?,price=?  where id=?";
				ps=con.prepareStatement(sql1);
				ps.setInt(1, quantity);
				ps.setInt(2, price);
				ps.setString(3, id);
				
				ps.executeUpdate();
				ps.close();
			}
			else{
				System.out.print("in sql 1");
				sql1="insert into update_daily_stock values (?,?,?,?)";
				
				PreparedStatement ps2=con.prepareStatement(sql1);
				ps2=con.prepareStatement(sql1);
				ps2.setString(1,id);
				ps2.setInt(2,quantity);
				ps2.setInt(3,price);
				ps2.setString(4,nowdate);
				
				ps2.executeUpdate();
		
			}
			
			return "success";
			
		} catch (SQLException e) {
			if(e.getSQLState().startsWith("23")){
				addActionError("WRONG ID");
				return "input";
			
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "failure";

	}
	public void validate(){
		if(id==null){
			addFieldError("id", "Need to fill it");
		}
		if(id.length()==10){
			addFieldError("id", "id should be only 10 charecter long");
		}
		if(quantity==0){
			addFieldError("quantity","Need to fill it");
		}
		if(price==0){
			addFieldError("price", "need to fill it");
		}
		if(price>150){
			addFieldError("price", "can't be greater than 150");
		}
	}

}
