<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String fn = request.getParameter("fn");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	img {
		width : 600px;
		height : 800px;
	}
</style>
</head>
<body>
	<img src="../image/<%=fn %>"/>
</body>
</html>