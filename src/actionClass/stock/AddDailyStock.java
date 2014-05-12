package actionClass.stock;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.sql.Connection;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import DatabaseConnection.sqlConnectivity;
public class AddDailyStock extends ActionSupport {
private String id,name,price;
String messname;
sqlConnectivity s=new sqlConnectivity();
public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getPrice() {
	return price;
}

public void setPrice(String price) {
	this.price = price;
}
public String execute(){
	Connection con;
	Map session=ActionContext.getContext().getSession();
	
	messname=(String) session.get("a");
	try {
		String Conname,uname,pwd;
		Conname=s.sql_connection;
		uname=s.uname;
		pwd=s.pwd;
		con=DriverManager.getConnection(Conname,uname,pwd);
					
		//String sql1="select mess_name from item where item_id=?";
		String sql2="insert into daily_stock values (?,?)";
		//
	/*	ps.setString(1, messname+id);
		ResultSet rs=ps.executeQuery();
		if(rs.next()){
			addActionError("Id Already exist");
			return "input";
		}
		else{*/
		PreparedStatement ps=con.prepareStatement(sql2);
			ps=con.prepareStatement(sql2);
			ps.setString(1, messname+id+"daily");
			ps.setString(2, name);
			//ps.setString(3, price);
			ps.executeUpdate();
		//}
		return "success";
		
	} catch (SQLException e) {
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
	if(name==null){
		addFieldError("name","Need to fill it");
	}
	if(name.length()==20){
		addFieldError("name", "name should be only 10 charecter long");
	}
}

}
