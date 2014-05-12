package test;

import java.text.DateFormat;
import java.text.ParseException;

import org.joda.time.Days;
import org.joda.time.DateTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
public class Test {
	private String selected_id;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Date d=new Date();
		Calendar cal=Calendar.getInstance();
		DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
		
		//System.out.println("curdate"+df.format(cal.getTime()));
		//cal.setTime(d);
		//int month=cal.get(Calendar.MONTH);
		//cal.set(Calendar.DATE,cal.getActualMinimum(Calendar.DATE));
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);
		
		String monthfirstdate=df.format(d);
		
		System.out.println(monthfirstdate);
		d=new Date();
		String presentdate=df.format(d);
		
		Date d1=null,d2=null;
		String dsa="2014-05-02 02:25:19DD165daily";
		if(dsa.matches(".*daily")){
			System.out.print("Ok");	
		System.out.print("Ok");}
		try {
			d1 = df.parse(monthfirstdate);
			d2 =df.parse(presentdate);
			 
			DateTime dt1 = new DateTime(d1);
			DateTime dt2 = new DateTime(d2);

			int numdays=Days.daysBetween(dt1,dt2).getDays();
			System.out.print(numdays);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
/*
package actionClass.student;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.print.Doc;

import actionClass.employee.payment_Employee;

import com.itextpdf.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
//import com.lowagie.text.pdf.PdfWriter;
import com.itextpdf.text.Section;
import com.itextpdf.text.Font;
public class generateBill implements ModelDriven{

	String message;
	
	private int messcharg;
	private String month;
	public String messname;
	
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
		      Font.BOLD);
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
		      Font.BOLD);
	public int getMesscharg() {
		return messcharg;
	}
	public void setMesscharg(int messcharg) {
		this.messcharg = messcharg;
	}
	Date d=new Date();
	
	public String execute(){
	
	Map session= ActionContext.getContext().getSession();
	messname=(String)session.get("a");
	try {
		Document doc=new Document();
		FileOutputStream file=new FileOutputStream(new File("/home/raj/Desktop/test.pdf"));
		PdfWriter writer = PdfWriter.getInstance(doc,file);
		doc.open();
		addMetadata(doc);
		addTitle(doc);
		PdfPTable t1=new PdfPTable(6);
		PdfPCell c1=new PdfPCell(new Phrase("S.no"));
		t1.addCell(c1);
		PdfPCell c2=new PdfPCell(new Phrase("Roll"));
		t1.addCell(c2);
		PdfPCell c3=new PdfPCell(new Phrase("Name"));
		t1.addCell(c3);
		PdfPCell c4=new PdfPCell(new Phrase("Extra"));
		t1.addCell(c4);
		PdfPCell c5=new PdfPCell(new Phrase("Mess days"));
		t1.addCell(c5);
		PdfPCell c6=new PdfPCell(new Phrase("Total"));
		t1.addCell(c6);
		
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
			String sql="select s_roll,s_name,extra,mess_days, mess_days * ? + extra as bill from mess_enrollment where mess_name=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, 74);
			ps.setString(2, messname);
		//	ps.setString(3,"%-"+month+"-%");
			ResultSet rs=ps.executeQuery();
			int i=0;
			
			while(rs.next()){
				t1.addCell(""+i);
				t1.addCell(rs.getString("s_roll"));
				t1.addCell(rs.getString("s_name"));
				t1.addCell(rs.getString("extra"));
				t1.addCell(rs.getString("mess_days"));
				t1.addCell(rs.getString("bill"));
				s.add(t1);
				i++;
			//	System.out.print("in billing");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//String temp="National Institute of Technology Calicut Mess Bill for ";
		
		
		
		//doc.add(t1);
		doc.close();
		
		return "success";
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
return "failure";

	
	
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public void addMetadata(Document doc){
		doc.addTitle("Mess Bill");
		doc.addSubject("Using iText");
	}
	public void addTitle(Document doc){
		Paragraph p=new Paragraph();
		addEmptyline(p,1);
		DateFormat df=new SimpleDateFormat("MMM");
		Calendar cal=Calendar.getInstance();
		p.add(new Paragraph("National Institute of Technology Calicut Mess Bill for mess"+messname,catFont));
		p.add(new Paragraph("Student Mess Bill for the month "+df.format(cal.getTime()),smallBold));
		p.add(new Paragraph("Mess per day charge="+messcharg,smallBold));
		try {
			doc.add(p);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addContent(Document d){
		PdfPTable t1=new PdfPTable(3);
		PdfPCell c1=new PdfPCell(new Phrase("S.no"));
		t1.addCell(c1);
		PdfPCell c2=new PdfPCell(new Phrase("Roll"));
		t1.addCell(c2);
		PdfPCell c3=new PdfPCell(new Phrase("Name"));
		t1.addCell(c3);
		PdfPCell c4=new PdfPCell(new Phrase("Extra"));
		t1.addCell(c4);
		PdfPCell c5=new PdfPCell(new Phrase("Mess days"));
		t1.addCell(c5);
		PdfPCell c6=new PdfPCell(new Phrase("Total"));
		t1.addCell(c6);
		
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/messproject","root","1234");
			String sql="select s_roll,s_name,extra,mess_days, mess_days * ? + extra as bill from mess_enrollment where mess_name=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, 74);
			ps.setString(2, messname);
		//	ps.setString(3,"%-"+month+"-%");
			ResultSet rs=ps.executeQuery();
			int i=0;
			System.out.print("in billing");
			while(rs.next()){
				t1.addCell(rs.getString("s_roll"));
				t1.addCell(rs.getString("s_name"));
				t1.addCell(rs.getString("extra"));
				t1.addCell(rs.getString("mess_days"));
				t1.addCell(rs.getString("bill"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int i=0;
		while(i<100){
			t1.addCell(""+i);
			i++;
		}
		s.add(t1);
	}
	public void addEmptyline(Paragraph p,int i){
		for(int j=0;j<i;j++){
			p.add(new Paragraph(""));
		}
	}
	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
*/