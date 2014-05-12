import java.util.ArrayList;


public class Fillmonth {
private ArrayList<String> month=new ArrayList<String>();
public ArrayList<String> getMonth() {
	return month;
}
public void setMonth(ArrayList<String> month) {
	this.month = month;
}
public String fill(){

month.add("1");
month.add("2");
month.add("3");
month.add("4");
month.add("5");
month.add("6");
month.add("7");
month.add("8");
month.add("9");
month.add("10");
month.add("11");
month.add("12");
return "filled";	
}}
