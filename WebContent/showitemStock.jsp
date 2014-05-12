<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Pragma" content="no-cache">  
 <meta http-equiv="Cache-Control" content="no-cache">  
 <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">  
 <%
response.setHeader("Cache-Control","no-store"); // HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0);
%>
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
					<li><a href="stock.jsp" class="button">Stock</a></li>
					<li><a href="student.jsp" class="button">Student</a></li>
					
					<li><a href='<s:url action="displayMonthinbillcharge"></s:url>'class="button">Mess Bill</a></li>
					<li><a href='<s:url action="logout"/>' class="button">Logout</a></li>
				</ul>
			</div>
			<h1>
				MessTrix<small>Welcome <s:property value="#session.a" /></small>
			</h1>
			
			<h2>
			
			</h2>
			<hr />
		</div>
	</div>

	<!-- End Nav -->


	<!-- Main Page Content and Sidebar -->

	<div class="row">

		<!-- Main Blog Content -->
		<div class="large-9 columns" role="content">

			<article>

			<h3>
				<s:date name="%{new java.util.Date()}" format="dd-MM-yyyy" />
			</h3>


			<div class="row">
				<div class="large-6 columns"></div>

			</div>

			<div class="scroll">

				<hr />
				<table>
					<thead>
						<tr>
							<th width="200">ID</th>
							<th width="200">Name</th>
							<th width="150">Price</th>
							<th width="150">Bought</th>
							<th width="150">Left</th>
							<th width="150">Vendor</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="itemd" var="temp">
							<tr>
								<td><s:property value="#temp.item_id" /></td>
								<td><s:property value="#temp.name" /></td>
								<td><s:property value="#temp.price" /></td>
								<td><s:property value="#temp.quantityBorrowed" /></td>
								<td><s:property value="#temp.quantityLeft" /></td>
								<td><s:property value="#temp.vendor" /></td>

							</tr>
						</s:iterator>
				</table>

			</div>
		</div>
		</article>




		<!-- End Main Content -->

		<!-- Sidebar -->

		<aside class="large-3 columns">

		<h5>Categories</h5>
		<ul class="side-nav">
			<li><a href="stock.jsp">Add Item</a></li>
			<li><a href='<s:url action="getItem"></s:url>'>Show Item
					Stock</a></li>
			<li><a href='<s:url action="autoComplete"></s:url>'>Update
					Stock</a></li>
			<li><a href='<s:url action="fillupdateDailyBox"></s:url>'>Update
					Daily Stock</a></li>
			<li><a href="addVendor.jsp">Add Vendor</a></li>
			<li><a href="AddDailystock.jsp">Add Daily Item</a></li>
			<li><a href="addBill.jsp">Add Bill</a></li>
			<li><a href='<s:url action="billDue"></s:url>'>Bill Due</a></li>
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
				<p>© Copyright Rajdeep Sabui</p>
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