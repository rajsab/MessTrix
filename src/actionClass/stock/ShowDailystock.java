package actionClass.stock;
import DatabaseConnection.sqlConnectivity;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import DatabaseConnection.sqlConnectivity;

import com.opensymphony.xwork2.ActionContext;
public class ShowDailystock {
	sqlConnectivity s=new sqlConnectivity();
	private ArrayList<String> id=new ArrayList<String>();
	Map session=ActionContext.getContext().getSession();
	String messname;
	public String execute(){
		try {
			messname=(String) session.get("a");
			String Conname,uname,pwd;
			Conname=s.sql_connection;
			uname=s.uname;
			pwd=s.pwd;
			Connection con=DriverManager.getConnection(Conname,uname,pwd);
						
			String sql="select id from daily_stock where id like ?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, messname+"%");
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
			while(rs.next()){
				id.add(rs.getString("id"));
			}}
			return"success";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return"failure";
	}
	/**
	 * @return the id
	 */
	public ArrayList<String> getId() {
		return id;
	}
}
