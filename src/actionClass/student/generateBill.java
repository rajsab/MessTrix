package actionClass.student;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.nio.file.Files;
import java.util.Set;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.Path;

import javax.print.Doc;

import actionClass.employee.payment_Employee;

import com.itextpdf.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.org.apache.xpath.internal.operations.Gte;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
//import com.lowagie.text.pdf.PdfWriter;
import com.itextpdf.text.Section;
import com.itextpdf.text.Font;
import DatabaseConnection.sqlConnectivity;
public class generateBill implements ModelDriven{
	sqlConnectivity sq=new sqlConnectivity();
	String message;
	Section s;
	private int messcharg;
	private String curmonth;
	private String filename;
	private FileOutputStream file;
	private InputStream fileInputStream;
	Map session= ActionContext.getContext().getSession();
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
		      Font.BOLD);
	public FileOutputStream getFile() {
		return file;
	}
	
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
	String messname=(String)session.get("a");
	
	try {
		Document doc=new Document();
	
		//file=new FileOutputStream(new File("/workspace/MessAutomation/WebContent/"+messname+".pdf"));
		file=new FileOutputStream(new File("/var/lib/tomcat7/webapps/MessAutomation/"+messname+".pdf"));
		Set<PosixFilePermissions> perms=new HashSet<PosixFilePermissions>();
		PdfWriter.getInstance(doc,file);
		doc.open();
		addMetadata(doc);
		addTitle(doc,messname);
		
		addContent(doc,messname);
		//String temp="National Institute of Technology Calicut Mess Bill for ";
		
		
		
		//doc.add(t1);
		doc.close();
		//setFileInputStream(new DataInputStream(new FileInputStream("/home/raj/Desktop/"+messname+".pdf")));
		System.out.println("bill generation complete");
		return "success";
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
return "failure";

	
	
	}public String getMonth() {
		return curmonth;
	}
	public void setMonth(String month) {
		this.curmonth = month;
	}
	public void addMetadata(Document doc){
		doc.addTitle("Mess Bill");
		doc.addSubject("Using iText");
	}
	public void addTitle(Document doc,String name){
		Paragraph p=new Paragraph();
		Paragraph p1=new Paragraph();
		addEmptyline(p,1);
		DateFormat df=new SimpleDateFormat("MMM");
		Calendar cal=Calendar.getInstance();
		p1.add(new Paragraph("National Institute of Technology Calicut",catFont));
		p.add(new Paragraph("Mess name : -"+name,catFont));
		p.add(new Paragraph("Student Mess Bill for the month "+df.format(cal.getTime()),smallBold));
		p.add(new Paragraph("Mess per day charge="+messcharg,smallBold));
		addEmptyline(p, 1);
		try {
			doc.add(p1);
			doc.add(new Paragraph(" "));
			doc.add(p);
			doc.add(new Paragraph(" "));
			doc.add(new Paragraph(" "));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addContent(Document d,String name) throws DocumentException{
		PdfPTable t1=new PdfPTable(6);
		t1.setTotalWidth(new float[] {20,40,40,40,40,40});
		

		PdfPCell c1=new PdfPCell(new Phrase("S.no"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		t1.addCell(c1);
		PdfPCell c2=new PdfPCell(new Phrase("Roll"));
		c2.setHorizontalAlignment(Element.ALIGN_CENTER);
		t1.addCell(c2);
		PdfPCell c3=new PdfPCell(new Phrase("Name"));
		c3.setHorizontalAlignment(Element.ALIGN_CENTER);
		t1.addCell(c3);
		PdfPCell c4=new PdfPCell(new Phrase("Extra"));
		c4.setHorizontalAlignment(Element.ALIGN_CENTER);
		t1.addCell(c4);
		PdfPCell c5=new PdfPCell(new Phrase("Mess days"));
		c5.setHorizontalAlignment(Element.ALIGN_CENTER);
		t1.addCell(c5);
		PdfPCell c6=new PdfPCell(new Phrase("Total"));
		c6.setHorizontalAlignment(Element.ALIGN_CENTER);
		t1.addCell(c6);
		
		
		try {
		String Conname,uname,pwd;
		Conname=sq.sql_connection;
		uname=sq.uname;
		pwd=sq.pwd;
		Connection con=DriverManager.getConnection(Conname,uname,pwd);
					
			String sql="select s_roll,s_name,extra,mess_days, mess_days * ? + extra as bill from mess_enrollment where mess_name=? and date_of_joining like ?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1,messcharg);
			ps.setString(2, name);
			ps.setString(3,"%-%"+curmonth+"-%");
			System.out.println(curmonth);
			ResultSet rs=ps.executeQuery();
			int i=1;
			System.out.print("in billing");
			while(rs.next()){
				PdfPCell ci=new PdfPCell(new Phrase(""+i));
				ci.setHorizontalAlignment(Element.ALIGN_CENTER);
				t1.addCell(ci);
				
				ci=new PdfPCell(new Phrase(rs.getString("s_roll")));
				ci.setHorizontalAlignment(Element.ALIGN_CENTER);
				t1.addCell(ci);
				
				ci=new PdfPCell(new Phrase(rs.getString("s_name")));
				ci.setHorizontalAlignment(Element.ALIGN_CENTER);
				t1.addCell(ci);
				
				ci=new PdfPCell(new Phrase(rs.getString("extra")));
				ci.setHorizontalAlignment(Element.ALIGN_CENTER);
				t1.addCell(ci);
				
				ci=new PdfPCell(new Phrase(rs.getString("mess_days")));
				ci.setHorizontalAlignment(Element.ALIGN_CENTER);
				t1.addCell(ci);
				
				ci=new PdfPCell(new Phrase(rs.getString("bill")));
				ci.setHorizontalAlignment(Element.ALIGN_CENTER);
				t1.addCell(ci);
				
				i++;
			}
			Paragraph p=new Paragraph();
			p.add(new Paragraph("			"));
			p.add(new Paragraph("			"));
			d.add(p);
			d.add(new Paragraph(" "));
			d.add(new Paragraph(" "));
			d.add(t1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public String getCurmonth() {
		return curmonth;
	}
	public void setCurmonth(String curmonth) {
		this.curmonth = curmonth;
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
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @param filename the filename to set
	 */
	/**
	 * @return the fileInputStream
	 */
	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	/**
	 * @param fileInputStream the fileInputStream to set
	 */
	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
	
	
}
