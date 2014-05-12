package actionClass.stock;
import DatabaseConnection.sqlConnectivity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.components.ActionError;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Add_item extends ActionSupport{
	private String name,vendor,id;
	Connection con=null;
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
		this.name = name.toUpperCase();
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
	sqlConnectivity s=new sqlConnectivity();
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
			hold=messname+id;String Conname,uname,pwd;
			Conname=s.sql_connection;
			uname=s.uname;
			pwd=s.pwd;
			con=DriverManager.getConnection(Conname,uname,pwd);
						
			con.setAutoCommit(false);
			/*String sqlv="select * from vendor where mess_name=? and id=?";
			PreparedStatement psv=con.prepareStatement(sqlv);
			psv.setString(1, messname);
			psv.setString(2, id);
			ResultSet rsv=psv.executeQuery();
		
			if(rsv.next()){*/
				System.out.println(messname);
			String sql0="select mess_name from item where item_id=?";
			String sql="insert into item (item_id,name,price,vender,mess_name) values (?,?,?,?,?)";
			PreparedStatement ps0=con.prepareStatement(sql0);
			ps0.setString(1, hold);
			ResultSet rs=ps0.executeQuery();
			if(rs.next()){
				addActionError("Id already exist");
				return "input";
			}
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1, hold);
			ps.setString(2,name);
			//ps.setInt(3, quantity);
			ps.setInt(3, price);
			ps.setString(4, vendor);
			ps.setString(5, messname);
			int i=0;
			i=ps.executeUpdate();
		
			String sql2="insert into item_extra values (?,0)";
			PreparedStatement ps2=con.prepareStatement(sql2);
			ps2.setString(1, hold);
			/*String sql3="insert into item_entry (item_id,id) values (?,?)";
			PreparedStatement ps3=con.prepareStatement(sql3);
			ps3.setString(1, id);
			ps3.setString(2, now);
		
			ps3.executeUpdate();
			*/
			ps2.executeUpdate();
			con.commit();
			return "success";
			}
		} catch (SQLException e) {
			System.out.println("Inside catch");
			// TODO Auto-generated catch block
			if(e.getSQLState().startsWith("23")){
				addActionError("Unknown Vendor Id");
				return "input";
			}
			//System.out.println(e);
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		
			return "failure";
			
		}
		//return temp;
	}
	public String call(){
		return "failure";
	}
	public void validate(){
		messname=(String) session.get("a");
		
		if(messname.isEmpty()){
			((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate(); //invalidates the session
			ActionContext.getContext().getSession().clear();//clear session value
			call();
		}
		if(getName().length()==0){
			addFieldError("name", "Please fill Stock name");
		}
		if(getName().length()>10){
			addFieldError("name", "Product name shouldn't be greater than 10");
		}
		if(getId().length()>4){
			addFieldError("id", "Id length shouldn't be greater than 4");
		}
		Pattern p=Pattern.compile("[^(a-z)\\s]",Pattern.CASE_INSENSITIVE);
		Matcher ma=p.matcher(name);
		boolean b=ma.find();
		if(b){
			addFieldError("name", "Name shouldn't contain any % or special charecters");
		}
		if(price==0){
			addFieldError("price", "Product must have a price");
		}
		if(getVendor().length()==0){
			addFieldError("vendor", "please fill the vendor name");
		}
	}

}
