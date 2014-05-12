package actionClass.stock;
import java.sql.*;

import DatabaseConnection.sqlConnectivity;
import java.util.ArrayList;
import java.util.Map;


import com.opensymphony.xwork2.ActionContext;
class item{
	private String name,item_id,quantityBorrowed,quantityLeft,price,vendor;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItem_id() {
		return item_id;
	}
	
	
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	/**
	 * @return the quantityBorrowed
	 */
	public String getQuantityBorrowed() {
		return quantityBorrowed;
	}

	/**
	 * @param quantityBorrowed the quantityBorrowed to set
	 */
	public void setQuantityBorrowed(String quantityBorrowed) {
		this.quantityBorrowed = quantityBorrowed;
	}

	/**
	 * @return the quantityLeft
	 */
	public String getQuantityLeft() {
		return quantityLeft;
	}

	/**
	 * @param quantityLeft the quantityLeft to set
	 */
	public void setQuantityLeft(String quantityLeft) {
		this.quantityLeft = quantityLeft;
	}

	/**
	 * @return the month
	 */
	}
public class Showitem {
	sqlConnectivity s=new sqlConnectivity();
	String send="failure";
	private String messname;
	private ArrayList<String> fillmonth=new ArrayList<String>();
	private String curmonth;
	private ArrayList<item> itemd=new ArrayList<item>();
	public ArrayList<item> getItemd() {
		return itemd;
	}
	public void setItemd(ArrayList<item> itemd) {
		this.itemd = itemd;
	}
	Map<?, ?> session = ActionContext.getContext().getSession();
	public String execute(){	
	try{
		messname=(String) session.get("a");
		String Conname,uname,pwd;
		Conname=s.sql_connection;
		uname=s.uname;
		pwd=s.pwd;
		Connection con=DriverManager.getConnection(Conname,uname,pwd);
					
		String sql="select i.item_id,i.name,i.price,v.name as vendor,sum(ie.quantity) as quantityBorrowed,iex.quantity as quantityLeft from vendor v,item i,item_entry ie,item_extra iex where i.mess_name=? and v.id=i.vender and i.item_id=iex.item_id and ie.item_id=i.item_id group by (iex.item_id)";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, getMessname());
		ResultSet rs=ps.executeQuery();
		System.out.print(getMessname());
		while (rs.next()) {
			System.out.print("Query for showing item successfull");	
			item i=new item();
			i.setItem_id(rs.getString("item_id"));
			i.setName(rs.getString("name"));
			i.setPrice(rs.getString("price"));
			i.setQuantityBorrowed(rs.getString("quantityBorrowed"));
			i.setQuantityLeft(rs.getString("quantityLeft"));
			i.setVendor(rs.getString("vendor"));
			itemd.add(i);
			
		}
		send="success";
}
	catch(SQLException e){
		System.out.print(e);
	}
	return send;
}

	public String getMessname() {
		return messname;
	}
	public void setMessname(String messname) {
		this.messname = messname;
	}
	public ArrayList<String> getFillmonth() {
		return fillmonth;
	}
	public void setFillmonth(ArrayList<String> fillmonth) {
		this.fillmonth = fillmonth;
	}
	/**
	 * @return the curmonth
	 */
	public String getCurmonth() {
		return curmonth;
	}
	/**
	 * @param curmonth the curmonth to set
	 */
	public void setCurmonth(String curmonth) {
		this.curmonth = curmonth;
	}
}