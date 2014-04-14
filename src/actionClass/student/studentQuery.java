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

import com.opensymphony.xwork2.ActionContext;
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

	/**
	 * @return the curMonth
	 */
	
}
public class studentQuery {
	Map session = ActionContext.getContext().getSession();
	private String roll,messname,curMonth;
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

	
	/*public String execute(){
		
	}*/
	public studentQuery(){
		month.add("1");
		month.add("2");
		month.add("3");
		month.add("4");
		month.add("5");
		month.add("6");
		month.add("7");
		month.add("8");
		month.add("9");
		month.add("10");
		month.add("11");
		month.add("12");
	}
	
	public String fillMonth(){
		return "filled";
	}
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
		curMonth=0+curMonth;
		try{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
			String sql="select e.id,e.quantity,i.name as name from extra e,item i where roll='"+roll+"' and e.itemid=i.item_id and e.mess_name='"+messname+"' and e.id like '%-"+curMonth+"-%'";
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				itemdetail i=new itemdetail();
				i.setId(rs.getString("id"));
				i.setName(rs.getString("name"));
				i.setQuantity(rs.getString("quantity"));
				extra.add(i);
			}
		return "success";
		}
		catch(SQLException e){
			System.out.println(e);
		
				((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
				ActionContext.getContext().getSession().clear();//clear session value
				return "failure";
			
		}
		
	}
}
