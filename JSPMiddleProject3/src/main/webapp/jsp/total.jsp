<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>

<%-- import --%>
<%-- 제어문, URL, 변수 선언 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- format --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- function:fn --%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- prefix는 임의로 결정할 수 있다 --%>

<%
	// 데이터 설정
	List<String> names = new ArrayList<String>();
	names.add("홍길동");
	names.add("심청이");
	names.add("이순신");
	names.add("박문수");
	names.add("강감찬");
	
	// request나 session에 저장해놔야 EL, JSTL 사용 가능
	//request.setAttribute("names", names);
	
	List<String> sexs = new ArrayList<String>();
	sexs.add("남자");
	sexs.add("여자");
	sexs.add("남자");
	sexs.add("남자");
	sexs.add("남자");
	
%>
<c:set var="names" value="<%=names %>"/>
<c:set var="sexs" value="<%=sexs %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>JSTL for</h3>
	<ul>
		<c:forEach var="name" items="${names }">
			<li>${name }</li>
		</c:forEach>
	</ul>

<%-- 	
	<ul>
		<c:forEach var="name" items="${names }" varStatus="s"> → list 인덱스 번호 가져올 때
			<li>${s.index+1 }.${names }</li>
			<li>${list[s.index] }</li>
		</c:forEach>
	</ul>
--%>
	
	<h3>JSTL forTokens(StringTokenizer)</h3>
	<ul>
		<c:forTokens var="color" items="red,blue,green,yellow,black" delims=",">
			<li>${color }</li>
		</c:forTokens>
	</ul>
	
	<h3>JSTL</h3>
	<ul>
		<c:forEach var="name" items="${names }" varStatus="s">
			<li>${name }(성별 : ${sexs[s.index] })</li>
		</c:forEach>
	</ul>
</body>
</html>