package actionClass.student;

import DatabaseConnection.sqlConnectivity;
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
import com.opensymphony.xwork2.ActionSupport;

public class Addextra extends ActionSupport{
	int flag=0;
	
	sqlConnectivity s=new sqlConnectivity();
	String sql4,sql5;
	Connection con;
	Date d=new Date();
	DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
	DateFormat full=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String send="failure";
	Map session = ActionContext.getContext().getSession();
	private String sroll,itemid;
	private int quantity;
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
		String Conname,uname,pwd;
		Conname=s.sql_connection;
		uname=s.uname;
		pwd=s.pwd;
		Connection con=DriverManager.getConnection(Conname,uname,pwd);
			
		con.setAutoCommit(false);
		String sql;
		System.out.print(""+itemid);
		PreparedStatement ps;
		if(getItemid().matches(".*daily")){
			sql="select price from update_daily_stock where id=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, itemid);
			System.out.print("1 in daily");
			flag=1;
		}else{
			sql="select price from item where item_id=? and mess_name=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, itemid);
			ps.setString(2, messname);
			
		}
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			price=Integer.parseInt(rs.getString("price"));
		}
		tot=price*quantity;
		String sqlprce="select extra from mess_enrollment where mess_name=? and s_roll=? and date_of_joining >=?";
		String sql2="update mess_enrollment set extra=extra+? where mess_name=? and s_roll=? and date_of_joining >=?";
		Calendar cals=Calendar.getInstance();
		id=full.format(cals.getTime())+messname;
		String sql3="insert into extra (id,quantity,mess_name,itemid,roll) values (?,?,?,?,?)";
		if(flag==1){
			sql5="select quantity from update_daily_stock where id=?";
			sql4="update update_daily_stock set quantity=quantity-? where id=?";
			System.out.print("2 in daily");
		}
		else{
			sql5="select quantity from item_extra where item_id=?";
			sql4="update item_extra set quantity=quantity-? where item_id=?";
		}
		PreparedStatement ps1=con.prepareStatement(sql2);
		PreparedStatement psp=con.prepareStatement(sqlprce);
		psp.setString(1, messname);
		psp.setString(2, sroll);
		psp.setString(3, monthfirstdate);
		ResultSet rsp=psp.executeQuery();
		int sumextra=0;
		while(rsp.next())
				sumextra=Integer.parseInt(rsp.getString("extra"));
		if(sumextra+tot>2000){
			return "reached";
		}
		ps1.setInt(1, tot);
		ps1.setString(2, messname);
		ps1.setString(3, sroll);
		ps1.setString(4, monthfirstdate);
		PreparedStatement psi=con.prepareStatement(sql5);
		psi.setString(1, itemid);
		ResultSet rs2=psi.executeQuery();
		int presentquan=0;
		while(rs2.next())
		presentquan=Integer.parseInt(rs2.getString("quantity"));
		if(presentquan-quantity>=0){
			System.out.print("1 in daily");
			ps1.executeUpdate();
			
			System.out.print("Ok quey1");
			PreparedStatement ps2=con.prepareStatement(sql3);
			ps2.setString(1, id);
			ps2.setInt(2, quantity);
			ps2.setString(3, messname);
			ps2.setString(4, itemid);
			ps2.setString(5, sroll);
			
			ps2.executeUpdate();
			System.out.print("Ok quey2");
			PreparedStatement ps3=con.prepareStatement(sql4);
			ps3.setInt(1, quantity);
			ps3.setString(2, itemid);
			ps3.executeUpdate();
			System.out.print("Ok quey3");
			System.out.println(monthfirstdate);
			send="success";
			con.commit();
			
		}
		else{
			return "noquantity";
		}
		}
		catch(SQLException ex){
			System.out.print(ex);
			((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				try {
					con.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	public String getSroll() {
		return sroll;
	}
	public void setSroll(String sroll) {
		this.sroll = sroll;
	}
	public void validate(){
		if(sroll.length()==0){
			addFieldError("sroll", "Need to fill roll");
		}
		if(getItemid().length()==0){
			addFieldError("itemid", "Need to fill item");
		}
		if(quantity==0){
			addFieldError("quantity", "Need to fill quantity");
		}
		
	}
}
