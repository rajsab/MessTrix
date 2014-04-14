<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Foundation | Welcome</title>
    <link rel="stylesheet" href="css/foundation.css" />
<title>Insert title here</title>
</head>
 <body>
       <!-- Nav Bar -->
  <div class="row">
    <div class="large-12 columns">
      <div class="nav-bar right">
       <ul class="button-group">
         <li><a href="Employee.jsp" class="button">Employee</a></li>
         <li><a href="stock.jsp" class="button">Stock</a></li>
         <li><a href="student.jsp" class="button">Student</a></li>
        </ul>
      </div>
      <h1>MessTricky <small>Automated solutions</small></h1>
      <small>Welcome <s:property value ="#session.a"/></small>   
      <h2><s:property value ="#session.name"/></h2>     
      <hr />
    </div>
  </div>
 
  <!-- End Nav -->
 
 
  <!-- Main Page Content and Sidebar -->
 
  <div class="row">
 
    <!-- Main Blog Content -->
    <div class="large-9 columns" role="content">
 
      <article>
 
        <h3> <s:date name="%{new java.util.Date()}" format="dd-MM-yyyy" /></h3>
        
 
        <div class="row">
          <div class="large-6 columns">
            
          </div>
         
        </div>
 
     
 
      <hr />
 <s:form action="showStudentQuery" >
 <s:textfield label="Roll-Number" key="roll"></s:textfield>
 <s:combobox list="month" name="curMonth" label="Choose Month"></s:combobox>
<s:submit></s:submit> 
  </s:form>
      
    </div>
   </article>
 
 
 

    <!-- End Main Content -->
 
    <!-- Sidebar -->
 
    <aside class="large-3 columns">
 
      <h5>Categories</h5>
      <ul class="side-nav">
        <li><a href="student.jsp">Student Entry</a></li>
        <li><a href="student_Extra.jsp">Student extra</a></li>
        <li><a href="messcut.jsp">Mess cut</a></li>
		<li><a href="'<s:url action="displayMonths"></s:url>'">Student Check</a></li>
      </ul>
 
     
 
    </aside>
 
    <!-- End Sidebar -->
  </div>
 
  <!-- End Main Content and Sidebar -->
 
 
  <!-- Footer -->
 
  <footer class="row">
    <div class="large-12 columns">
      <hr />
      <div class="row">
        <div class="large-6 columns">
          <p>© Copyright no one at all. Go to town.</p>
        </div>
        <div class="large-6 columns">
          <ul class="inline-list right">
          </ul>
        </div>
      </div>
    </div>
  </footer>
    
  </body>
</html>