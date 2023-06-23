<!--
		application
		  서버 관리(→ 서버, 로그에 대한 정보를 가지고 있음)
		  자원 관리
		  1) getServerInfo() : 서버 이름
		  2) getMajorVersion()
		  3) getMinorVersion()
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String driver = application.getInitParameter("driver");
		String url = application.getInitParameter("url");
		String username = application.getInitParameter("username");
		String password = application.getInitParameter("password");
		
/*		System.out.println("driver : "+driver);
		System.out.println("url : "+url);
		System.out.println("username : "+username);
		System.out.println("password : "+password);
*/		
		application.log("driver : "+driver);
		application.log("url : "+url);
		application.log("username : "+username);
		application.log("password : "+password);
		
		String path = application.getRealPath("/");
		System.out.println(path);
	%>
</body>
</html>