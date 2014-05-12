package actionClass.student;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import DatabaseConnection.sqlConnectivity;
import DatabaseConnection.sqlConnectivity;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LocatorEx.Snapshot;
class itemdetail{

	private String id,quantity,name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}


public class studentQuery extends ActionSupport{
	Map session = ActionContext.getContext().getSession();
	sqlConnectivity s=new sqlConnectivity();
	private String roll,messname,curMonth,total;
	public String getTotal() {
		return total;
	}
	public String getCurMonth() {
		return curMonth;
	}
	public void setCurMonth(String curMonth) {
		this.curMonth = curMonth;
	}

	Date d=new Date();
	DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
	private ArrayList<String> month = new ArrayList<String>();
	private ArrayList<itemdetail> extra =new ArrayList<itemdetail>();
	public ArrayList<itemdetail> getExtra() {
		return extra;
	}
	public void setExtra(ArrayList<itemdetail> extra) {
		this.extra = extra;
	}
	public ArrayList<String> getMonth() {
		return month;
	}
	/**
	 * @return the roll
	 */
	public String getRoll() {
		return roll;
	}
	/**
	 * @param roll the roll to set
	 */

	public void setRoll(String roll) {
		this.roll = roll;
	}
	public void setMonth(ArrayList<String> month) {
		this.month = month;
	}

	@SuppressWarnings("rawtypes")
	public String execute(){
	
		Map session = ActionContext.getContext().getSession();

		messname=(String) session.get("a");
		if(messname==null){
			((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
			ActionContext.getContext().getSession().clear();//clear session value
			return "failure";
		}
		String date=df.format(d);
		
		try{
			String Conname,uname,pwd;
			Conname=s.sql_connection;
			uname=s.uname;
			pwd=s.pwd;
			Connection con=DriverManager.getConnection(Conname,uname,pwd);
						
			String sql="select e.id,e.quantity,e.itemid from extra e where roll=? and e.mess_name=? and e.id like ?";
			
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, roll);
			ps.setString(2, messname);
			ps.setString(3, "%-%"+curMonth+"-%");

			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				itemdetail i=new itemdetail();
				i.setId(rs.getString("id"));
				i.setName(rs.getString("itemid"));
				i.setQuantity(rs.getString("quantity"));
				extra.add(i);
			}
			sql="select extra from mess_enrollment where s_roll=? and mess_name=? and date_of_joining like ?";
			ps=con.prepareStatement(sql);
			ps.setString(1, roll);
			ps.setString(2, messname);
			ps.setString(3, "%-%"+curMonth+"-%");
			rs=ps.executeQuery();
			if(rs.next()){
				total=rs.getString("extra");
			}
			System.out.println(total);
		return "success";
		}
		catch(SQLException e){
			System.out.println(e);
		
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				return "failure";
			
		}
		
	}
	
	public void validate(){
		if(curMonth.length()==0){
			addFieldError("curMonth", "please fill current month");
		}
		if(roll.length()==0){
			addFieldError("roll", "please fill roll");
		}
	}
}