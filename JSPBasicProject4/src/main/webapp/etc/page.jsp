<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%!
 	String msg="Hello JSP!!";
 	public String display()
 	{
	 	return msg;
 	}
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String msg="Hello JSP(지역변수)";
	%>
	<%=this.msg %>
</body>
</html>