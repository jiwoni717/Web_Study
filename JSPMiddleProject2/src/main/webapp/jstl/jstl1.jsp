<%--
		JSTL
		태그로 자바 문법을 만든 태그 라이브러리(XML)
		core : 자바 제어문, 변수 선언, 화면 이동
		  조건문 → 단일 조건문만 사용이 가능하다
		  	<if test="조건문">
		  	  조건문이 true일 때 실행되는 문장
		  	</if>
		  선택문 → 다중 조건문으로 사용할 수 있다
		    <choose>
		      <when text="">
		      </when>
		      ...
		      <otherwise></otherwise> → else / default
		    </choose>
		  반복문
		    일반 for → step을 음수로 설정하는 것은 불가능
		      <c:forEach var="i" begin="1" end="10" step="1">
		        반복 수행 문장
		      </c:forEach>
		    for-each
		      <c:forEach var="vo" item="list">
		      </c:forEach>
		    StringTokenizer
		      <c:forTokens>
		      </c:forTokens>
		  화면 이동(url)
		  	response.sendRedirect(url)
		      <c:redirect url="url">
		    request.setAttribute("key",값)
		      <c:set var="a" value="값">
		    out.println
		      <c:out value="">
		
		fmt : 날짜 변환, 숫자 변환
		  SimpleDateFormat
		    <fmt:formatDate value="" pattern="yyyy-MM-dd">
		  DecimalFormat
		  	<fmt:fotmatNumber value="" pattern="999,999">
		
		fn : String클래스의 메소드 이용
		  ${fn:length(문자열) }
		  ${fn:substring(문자열, start, end) }
		  
		sql : DAO
		
		xml : OXM
		  자바 자체에서 처리 → 사용 빈도가 거의 없다
--%>
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
	<h2>jstl</h2>
	<c:forEach var="i" begin="1" end="10" step="1">
		${i }&nbsp;
	</c:forEach>
</body>
</html>