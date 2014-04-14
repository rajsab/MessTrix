package actionClass.stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
class bill{
	private String vendor_name,due;
	public String getVendor_name() {
		return vendor_name;
	}
	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}
	public String getDue() {
		return due;
	}
	public void setDue(String due) {
		this.due = due;
	}
}
public class DueBill {

	private ArrayList<bill> complete=new ArrayList<bill>();
	public String execute(){
		Map session=ActionContext.getContext().getSession();
		String messname=(String)session.get("a");
		if(messname==null){
			((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
			ActionContext.getContext().getSession().clear();//clear session value
			return "failure";
		}else{
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
			String sql="select vendor_name,sum(amount)-sum(amount_paid) as due from bill group by vendor_name";
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
		
			while(rs.next()){
				
				bill b=new bill();
				b.setVendor_name(rs.getString("vendor_name"));
				b.setDue(rs.getString("due"));
				System.out.print("Hello");
				complete.add(b);
			}
			return "success";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		return "failure";
		
	}
	/**
	 * @return the complete
	 */
	public ArrayList<bill> getComplete() {
		return complete;
	}
	/**
	 * @param complete the complete to set
	 */
	public void setComplete(ArrayList<bill> complete) {
		this.complete = complete;
	}
}
