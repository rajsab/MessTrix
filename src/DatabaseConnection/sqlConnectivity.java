package DatabaseConnection;
import java.sql.*;
public class sqlConnectivity {
	private Connection con;
	
	public Connection getCon() {
		return con;
	}
	public void setCon(Connection con) {
		try {
			this.con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}