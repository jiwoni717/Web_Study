<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	메소드 방식 : <%=request.getMethod() %><br>
	<%-- post방식 : 자바스크립트, <form> --%>
	서버 주소 : <%=request.getServerName() %><br>
	서버 포트 : <%=request.getServerPort() %><br>
	클라이언트 아이피*** : <%=request.getRemoteAddr() %><br>
	<%-- 같은 아이피는 조회수 증가x / WebSocket : 실시간 상담 --%>
	URL*** : <%=request.getRequestURL() %><br>
	URI*** : <%=request.getRequestURI() %><br>
	ContextPath*** : <%=request.getContextPath() %><br>
	브라우저 : <%=request.getHeader("User-Agent") %><br>
</body>
</html>