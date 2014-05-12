import java.util.Map;

import com.opensymphony.xwork2.ActionContext;


public class Logout {
Map v;
	Map session=ActionContext.getContext().getSession();
	public String execute(){
		session.remove("a");
		String messname=(String) session.get("a");
		((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate();
		//ActionContext.getContext().getSession().clear();//clear session value
		System.out.println(""+messname);
		//.put("a", null)
		return "success";
	}
}
