package actionClass.stock;
import DatabaseConnection.sqlConnectivity;
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
	sqlConnectivity s=new sqlConnectivity();
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
			String Conname,uname,pwd;
			Conname=s.sql_connection;
			uname=s.uname;
			pwd=s.pwd;
			Connection con=DriverManager.getConnection(Conname,uname,pwd);

			String sql="insert into bill values (?,?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, Vid+Bid);
			ps.setString(2, date);
			ps.setInt(3, total);
			ps.setInt(4, payment);
			ps.setString(5, Vid);
			ps.executeUpdate();
			return "success";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(e.getSQLState().startsWith("23")){
				addActionError("Vendor id wrong or same Bill number");
				return "input";
			}
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
	public void validate(){
		if(Vid.length()==0){
			addFieldError("Vid", "Need to give vendor name");
		}
		if(Bid.length()==0){
			addFieldError("bid", "Need to give bill id");
			
		}
		if(payment==0){
			addFieldError("payment", "Need to fill Payment");
			
		}
		if(total==0){
			addFieldError("total", "Need to fill Total");
			
		}
		if(date.length()==0){
			addFieldError("date", "Please fill the date");
		}
		
	}

}
