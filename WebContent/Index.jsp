<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/simpleown.css">
</head>
<div class="Middlebox">
<table>
<tr align="left">
<td class="Big"> Login </td></tr></table>
<s:form action="login">
<s:textfield label="Userid" key="userid"></s:textfield></hline>
<s:password label="Password" key="password"></s:password>
<s:textfield label="Messname" key="m_name"></s:textfield>
<s:submit></s:submit>
</s:form>

</div>


</body>
</html>