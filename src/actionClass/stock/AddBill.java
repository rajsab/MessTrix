package actionClass.stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.sql.PreparedStatement;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddBill extends ActionSupport {
	Date d=new Date();
	DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
	private String Vid,Bid,date;
	private int payment,total;
	public String getVid() {
		return Vid;
	}
	public void setVid(String vid) {
		Vid = vid;
	}
	public String getBid() {
		return Bid;
	}
	public void setBid(String bid) {
		Bid = bid;
	}
	public int getPayment() {
		return payment;
	}
	public void setPayment(int payment) {
		this.payment = payment;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String execute(){
	
		Map session = ActionContext.getContext().getSession();
		String messname=(String) session.get("a");
		if(messname==null){
			((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
			ActionContext.getContext().getSession().clear();//clear session value
			return "failure";
		}else{
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
			String sql="insert into bill values (?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, Vid+Bid);
			ps.setString(2, date);
			ps.setInt(3, total);
			ps.setInt(4, payment);
			ps.executeUpdate();
			return "success";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		return "failure";
		
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	

}
