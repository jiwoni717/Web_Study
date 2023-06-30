<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("name", "홍길동");
	session.setAttribute("name", "심청이");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
이름 : ${name }, ${requestScope.name }
<%=request.getAttribute("name") %>
<%-- ↑ 두개 같은거 --%>
</body>
</html>