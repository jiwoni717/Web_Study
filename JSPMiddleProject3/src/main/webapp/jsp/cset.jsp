<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String name="홍길동";
	//request.setAttribute("name", name);
%>
<c:set var="name" value="심청이"/>
<%-- EL에서만 출력이 가능하게 변수 설정 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	이름 : ${name }<br>
	이름 : <c:out value="${name }"/><br>
	이름 : <c:out value="<%=name %>"/>
</body>
</html>