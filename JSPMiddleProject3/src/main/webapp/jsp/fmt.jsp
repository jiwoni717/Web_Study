<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, java.text.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Date today = new Date();
	int a = 12345678;
%>
<c:set var="today" value="<%=today %>"/>
<c:set var="a" value="<%=a %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>JSTL formatDate</h2>
	${today }<br>
	<fmt:formatDate value="${today }" pattern="yyyy-MM-dd"/>
	
	<h2>JSTL formatNumber</h2>
	${a }<br>
	<fmt:formatNumber value="${a }" type="currency"/>
</body>
</html>