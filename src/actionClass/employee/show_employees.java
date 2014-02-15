package actionClass.employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

class employee{
	private String employee_name,mob,wage,gender;

	/**
	 * @return the employee_name
	 */
	public String getEmployee_name() {
		return employee_name;
	}

	/**
	 * @param employee_name the employee_name to set
	 */
	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	/**
	 * @return the mob
	 */
	public String getMob() {
		return mob;
	}

	/**
	 * @param mob the mob to set
	 */
	public void setMob(String mob) {
		this.mob = mob;
	}

	/**
	 * @return the wage
	 */
	public String getWage() {
		return wage;
	}

	/**
	 * @param wage the wage to set
	 */
	public void setWage(String wage) {
		this.wage = wage;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

}
public class show_employees extends ActionSupport {
	String send="failure";
	private String messname;
	
	private ArrayList<employee> name=new ArrayList<employee>();
	Map<?, ?> session = ActionContext.getContext().getSession();
	public String execute(){
		
	try{
		messname=(String) session.get("a");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
		String sql1="select name,wage,mob,gender from employee where mess_name='"+messname+"'";
		PreparedStatement ps=con.prepareStatement(sql1);
		ResultSet rs=ps.executeQuery();
		System.out.println("Ok till here 1");
		while(rs.next()){
			employee e=new employee();
			e.setEmployee_name(rs.getString("name"));
			e.setWage(rs.getString("wage"));
			e.setMob(rs.getString("mob"));
			e.setGender(rs.getString("gender"));
		//	D_O_J=rs.getString("D_O_J");
		//	full=employee_name+" "+wage+" "+mob+" "+gender+" "+D_O_J;
			name.add(e);
			send="success";
		}
		
		
	}
	catch(SQLException e){
		System.out.println(e);
	}
	return send;
	}
	/**
	 * @return the messname
	 */
	public String getMessname() {
		return messname;
	}

	/**
	 * @param messname the messname to set
	 */
	public void setMessname(String messname) {
		this.messname = (String) session.get("a");
	}
	public void setName(ArrayList<employee> name) {
		this.name = name;
	} 
	
	public ArrayList<employee> getName() {
		  return name;
		}

}
