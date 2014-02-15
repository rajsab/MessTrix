<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title></title>
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
         <li><a href="#" class="button">Stock</a></li>
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
 <s:form action="student-Entry">
 <s:textfield label="Name" key="ename"></s:textfield>
 <s:textfield label="Wage" key="wage"></s:textfield>
 <s:textfield label="Mobile Number " key="mob"></s:textfield>
 <s:radio label="gender" name="gender" list="{'male','female'}"></s:radio> 
<s:submit></s:submit> 
  </s:form>
      
    </div>
   </article>
 
 
 

    <!-- End Main Content -->
 
    <!-- Sidebar -->
 
    <aside class="large-3 columns">
 
      <h5>Categories</h5>
      <ul class="side-nav">
         <li><a href="">Employee Detail</a></li>
        <li><a href="Add_employee.jsp">Add Employee</a></li>
        <li><a href="Edit_employee.jsp">Edit Employee</a></li>
        <li><a href="Advance_payment.jsp">Advance Payment</a></li>
        <li><a href="Employee_payment.jsp">Payment</a></li>
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
            <li><a href="#">Link 1</a></li>
            <li><a href="#">Link 2</a></li>
            <li><a href="#">Link 3</a></li>
            <li><a href="#">Link 4</a></li>
          </ul>
        </div>
      </div>
    </div>
  </footer>
    
  </body>
</html>