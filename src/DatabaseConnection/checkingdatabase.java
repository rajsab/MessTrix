package DatabaseConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ActionClass.LoginAction;
import ActionClass.LoginAction;
public class checkingdatabase {
	String userid=null;
sqlConnectivity sqlcon=new sqlConnectivity();
public String checkDetails(String username,String password){

try{
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
	String sql="select userid from test where username='"+username+"' and password='"+password+"'";
	PreparedStatement ps=con.prepareStatement(sql);
	ResultSet rs=ps.executeQuery();
	
	if(rs.next()){
		System.out.print("Hello");
		userid=rs.getString(1);
		
		return userid;
	}
}
catch(SQLException ex){
	System.out.print(ex);
}
return null;
}

}
