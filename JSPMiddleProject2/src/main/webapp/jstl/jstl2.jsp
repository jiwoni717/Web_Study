<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>JSTL if</h2>
	<c:forEach var="i" begin="1" end="10">
		<c:if test="${i%2==0 }">
			${i }&nbsp;
		</c:if>
	</c:forEach>
	
	<h2>JSTL switch case(=choose)</h2>
	<c:set var="i" value="4"/>
	<c:choose>
		<c:when test="${i==0 }">☆☆☆☆☆</c:when>
		<c:when test="${i==1 }">★☆☆☆☆</c:when>
		<c:when test="${i==2 }">★★☆☆☆</c:when>
		<c:when test="${i==3 }">★★★☆☆</c:when>
		<c:when test="${i==4 }">★★★★☆</c:when>
		<c:when test="${i==5 }">★★★★★</c:when>
	</c:choose>
</body>
</html>