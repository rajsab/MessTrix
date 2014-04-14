package actionClass.stock;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;


import com.opensymphony.xwork2.ActionContext;
class item{
	private String name,item_id,quantity,price,vendor;
	
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
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
	 * @return the month
	 */
	}
public class Showitem {
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
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
		String sql="select i.item_id,i.name,i.price,ie.Quantity,v.name as vendor from item i,vendor v,item_entry ie where i.mess_name=? and i.vender=v.id and ie.D_O_P like ?";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, getMessname());
		ps.setString(2, "%-_"+curmonth+"-%");
		ResultSet rs=ps.executeQuery();
		System.out.print(getMessname());
		while (rs.next()) {
			System.out.print("Query for showing item successfull");	
			item i=new item();
			i.setItem_id(rs.getString("item_id"));
			i.setName(rs.getString("name"));
			i.setPrice(rs.getString("price"));
			i.setQuantity(rs.getString("Quantity"));
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
	
	public  Showitem() {
		// TODO Auto-generated constructor stub
		fillmonth.add("1");
		fillmonth.add("2");
		fillmonth.add("3");
		fillmonth.add("4");
		fillmonth.add("5");
		fillmonth.add("6");
		fillmonth.add("7");
		fillmonth.add("8");
		fillmonth.add("9");
		fillmonth.add("10");
		fillmonth.add("11");
		fillmonth.add("12");

	}

	public String showmonth(){
		return "filled";
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